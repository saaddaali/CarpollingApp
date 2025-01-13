package ma.zyn.app.service.impl.passenger.driver;


import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.MessageCreateParams;
import com.anthropic.models.MessageParam;
import com.anthropic.models.Model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ma.zyn.app.utils.exception.EntityNotFoundException;
import ma.zyn.app.bean.core.driver.Driver;
import ma.zyn.app.dao.criteria.core.driver.DriverCriteria;
import ma.zyn.app.dao.facade.core.driver.DriverDao;
import ma.zyn.app.dao.specification.core.driver.DriverSpecification;
import ma.zyn.app.service.facade.passenger.driver.DriverPassengerService;

import static ma.zyn.app.utils.util.ListUtil.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ma.zyn.app.utils.util.RefelexivityUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import ma.zyn.app.utils.security.service.facade.UserService;
import ma.zyn.app.utils.security.service.facade.RoleService;
import ma.zyn.app.utils.security.service.facade.RoleUserService;
import ma.zyn.app.utils.security.bean.Role;
import ma.zyn.app.utils.security.bean.RoleUser;
import ma.zyn.app.utils.security.common.AuthoritiesConstants;
import ma.zyn.app.utils.security.service.facade.ModelPermissionUserService;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class DriverPassengerServiceImpl implements DriverPassengerService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Driver update(Driver t) {
        Driver loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Driver.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Driver findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Driver findOrSave(Driver t) {
        if (t != null) {
            Driver result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Driver> findAll() {
        return dao.findAll();
    }

    public List<Driver> findByCriteria(DriverCriteria criteria) {
        List<Driver> content = null;
        if (criteria != null) {
            DriverSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private DriverSpecification constructSpecification(DriverCriteria criteria) {
        DriverSpecification mySpecification =  (DriverSpecification) RefelexivityUtil.constructObjectUsingOneParam(DriverSpecification.class, criteria);
        return mySpecification;
    }

    public List<Driver> findPaginatedByCriteria(DriverCriteria criteria, int page, int pageSize, String order, String sortField) {
        DriverSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(DriverCriteria criteria) {
        DriverSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean deleteById(Long id) {
        boolean condition = (id != null);
        if (condition) {
            dao.deleteById(id);
        }
        return condition;
    }


    private static String geminiKey="AIzaSyC00otxmi8U-TrUtEU9ni_XP_6g64hFKYI";
    private final String API_URL_TEMPLATE = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-8b-latest:generateContent?key=%s";
    private final RestTemplate restTemplate = new RestTemplate();  // Use Spring's RestTemplate


    // Method to verify a driver's name by extracting it from an image and comparing it to the provided full name
    public boolean verifyDriver(String cinPhoto, String fullName) {
        // Send the request to Gemini to extract the name from the driver's photo
        String extractedName = extractTextFromPhoto(cinPhoto);

        // If extraction failed or returned "Unknown", return false
        if (extractedName.equals("Unknown")) {
            System.out.println("Failed to extract name from the image.");
            return false;
        }

        // Compare the extracted name with the provided full name
        return compareNames(extractedName, fullName);
    }

    // Method to send the prompt and extract the text from the photo using Gemini API
    private String extractTextFromPhoto(String cinPhoto) {
        try {
            String prompt = "Extract the name from this image: " + cinPhoto;

            // Create the API request
            String apiUrl = String.format(API_URL_TEMPLATE, geminiKey);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            // Create JSON body
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode contentNode = objectMapper.createObjectNode();
            ObjectNode partsNode = objectMapper.createObjectNode();
            partsNode.put("text", prompt);  // Include the prompt in the request
            contentNode.set("parts", objectMapper.createArrayNode().add(partsNode));
            ObjectNode requestBodyNode = objectMapper.createObjectNode();
            requestBodyNode.set("contents", objectMapper.createArrayNode().add(contentNode));

            // Convert the request body to JSON string
            String requestBody = objectMapper.writeValueAsString(requestBodyNode);
            System.out.println("Request Body: " + requestBody);

            // Create HTTP entity with the request body and headers
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            // Send the request and get the response
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);
            System.out.println("Response: " + response.getBody());

            // Parse the response to extract the name
            return parseGeminiResponse(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    // Parse the JSON response to extract the name (assumes the API returns a "completion" field with the name)
    // Parse the JSON response to extract the name
    private String parseGeminiResponse(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // Navigate to the candidates array
            JsonNode candidatesNode = rootNode.path("candidates");

            if (candidatesNode.isArray() && candidatesNode.size() > 0) {
                // Extract the text from the first candidate's "content" -> "parts" -> "text"
                JsonNode textNode = candidatesNode.get(0).path("content").path("parts").get(0).path("text");

                if (!textNode.isMissingNode()) {
                    String extractedText = textNode.asText().trim();
                    return extractedText.split("\n")[0].trim(); // Get the first line (name)
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown";
    }


    // Compare the extracted name with the full name (using Levenshtein distance or simple comparison)
    private boolean compareNames(String extractedName, String fullName) {
        extractedName = extractedName.replaceAll("[^a-zA-Z]", "").toLowerCase();
        fullName = fullName.replaceAll("[^a-zA-Z]", "").toLowerCase();

        // Levenshtein distance logic
        int n = extractedName.length();
        int m = fullName.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= n; i++) {
            char c1 = extractedName.charAt(i - 1);
            for (int j = 1; j <= m; j++) {
                char c2 = fullName.charAt(j - 1);
                if (c1 == c2) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    int replace = dp[i - 1][j - 1] + 1;
                    int insert = dp[i][j - 1] + 1;
                    int delete = dp[i - 1][j] + 1;
                    dp[i][j] = Math.min(replace, Math.min(insert, delete));
                }
            }
        }

        // Return true if the Levenshtein distance is less than a threshold (e.g., 3)
        return dp[n][m] < 3;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Driver> delete(List<Driver> list) {
		List<Driver> result = new ArrayList();
        if (list != null) {
            for (Driver t : list) {
                if(dao.findById(t.getId()).isEmpty()){
					result.add(t);
				}else{
                    dao.deleteById(t.getId());
                }
            }
        }
		return result;
    }


    public Driver findWithAssociatedLists(Long id){
        Driver result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Driver> update(List<Driver> ts, boolean createIfNotExist) {
        List<Driver> result = new ArrayList<>();
        if (ts != null) {
            for (Driver t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Driver loadedItem = dao.findById(t.getId()).orElse(null);
                    if (isEligibleForCreateOrUpdate(createIfNotExist, t, loadedItem)) {
                        dao.save(t);
                    } else {
                        result.add(t);
                    }
                }
            }
        }
        return result;
    }


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Driver t, Driver loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Driver findByReferenceEntity(Driver t){
        return t==null? null : dao.findByEmail(t.getEmail());
    }



    public List<Driver> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Driver>> getToBeSavedAndToBeDeleted(List<Driver> oldList, List<Driver> newList) {
        List<List<Driver>> result = new ArrayList<>();
        List<Driver> resultDelete = new ArrayList<>();
        List<Driver> resultUpdateOrSave = new ArrayList<>();
        if (isEmpty(oldList) && isNotEmpty(newList)) {
            resultUpdateOrSave.addAll(newList);
        } else if (isEmpty(newList) && isNotEmpty(oldList)) {
            resultDelete.addAll(oldList);
        } else if (isNotEmpty(newList) && isNotEmpty(oldList)) {
			extractToBeSaveOrDelete(oldList, newList, resultUpdateOrSave, resultDelete);
        }
        result.add(resultUpdateOrSave);
        result.add(resultDelete);
        return result;
    }

    private void extractToBeSaveOrDelete(List<Driver> oldList, List<Driver> newList, List<Driver> resultUpdateOrSave, List<Driver> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Driver myOld = oldList.get(i);
                Driver t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Driver myNew = newList.get(i);
                Driver t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }


    @Override
    public Driver create(Driver t) {
        if (findByUsername(t.getUsername()) != null || t.getPassword() == null) return null;
        t.setPassword(userService.cryptPassword(t.getPassword()));
        t.setEnabled(true);
        t.setAccountNonExpired(true);
        t.setAccountNonLocked(true);
        t.setCredentialsNonExpired(true);
        t.setPasswordChanged(false);

        Role role = new Role();
        role.setAuthority(AuthoritiesConstants.DRIVER);
        role.setCreatedAt(LocalDateTime.now());
        Role myRole = roleService.create(role);
        RoleUser roleUser = new RoleUser();
        roleUser.setRole(myRole);
        if (t.getRoleUsers() == null)
            t.setRoleUsers(new ArrayList<>());

        t.getRoleUsers().add(roleUser);
        if (t.getModelPermissionUsers() == null)
            t.setModelPermissionUsers(new ArrayList<>());

        t.setModelPermissionUsers(modelPermissionUserService.initModelPermissionUser());

        Driver mySaved = dao.save(t);

        if (t.getModelPermissionUsers() != null) {
            t.getModelPermissionUsers().forEach(e -> {
                e.setUser(mySaved);
                modelPermissionUserService.create(e);
            });
        }
        if (t.getRoleUsers() != null) {
            t.getRoleUsers().forEach(element-> {
                element.setUser(mySaved);
                roleUserService.create(element);
            });
        }

        return mySaved;
     }

    public Driver findByUsername(String username){
        return dao.findByUsername(username);
    }

    public boolean changePassword(String username, String newPassword) {
        return userService.changePassword(username, newPassword);
    }




    private @Autowired UserService userService;
    private @Autowired RoleService roleService;
    private @Autowired ModelPermissionUserService modelPermissionUserService;
    private @Autowired RoleUserService roleUserService;


    public DriverPassengerServiceImpl(DriverDao dao) {
        this.dao = dao;
    }

    private DriverDao dao;
}
