package  ma.zyn.app.dao.criteria.core.trajet;


import ma.zyn.app.dao.criteria.core.driver.DriverCriteria;

import ma.zyn.app.utils.criteria.BaseCriteria;

import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDateTime;

public class TrajetCriteria extends  BaseCriteria  {

    private LocalDateTime horaireDepart;
    private LocalDateTime horaireDepartFrom;
    private LocalDateTime horaireDepartTo;
    private LocalDateTime horaireArrive;
    private LocalDateTime horaireArriveFrom;
    private LocalDateTime horaireArriveTo;
    private String placesDisponibles;
    private String placesDisponiblesMin;
    private String placesDisponiblesMax;

    private String placesMax;
    private String placesMaxMin;
    private String placesMaxMax;
    private LocalDateTime dateCreation;
    private LocalDateTime dateCreationFrom;
    private LocalDateTime dateCreationTo;

    private VilleCriteria villeDepart ;
    private List<VilleCriteria> villeDeparts ;
    private VilleCriteria villeDestination ;
    private List<VilleCriteria> villeDestinations ;
    private DriverCriteria driver ;
    private List<DriverCriteria> drivers ;
    private VilleCriteria localisationSource ;
    private List<VilleCriteria> localisationSources ;
    private VilleCriteria localisationDestination ;
    private List<VilleCriteria> localisationDestinations ;

    private BigDecimal prix;
    private BigDecimal prixMin;
    private BigDecimal prixMax;


    public LocalDateTime getHoraireDepart(){
        return this.horaireDepart;
    }
    public void setHoraireDepart(LocalDateTime horaireDepart){
        this.horaireDepart = horaireDepart;
    }
    public LocalDateTime getHoraireDepartFrom(){
        return this.horaireDepartFrom;
    }
    public void setHoraireDepartFrom(LocalDateTime horaireDepartFrom){
        this.horaireDepartFrom = horaireDepartFrom;
    }
    public LocalDateTime getHoraireDepartTo(){
        return this.horaireDepartTo;
    }
    public void setHoraireDepartTo(LocalDateTime horaireDepartTo){
        this.horaireDepartTo = horaireDepartTo;
    }
    public LocalDateTime getHoraireArrive(){
        return this.horaireArrive;
    }
    public void setHoraireArrive(LocalDateTime horaireArrive){
        this.horaireArrive = horaireArrive;
    }
    public LocalDateTime getHoraireArriveFrom(){
        return this.horaireArriveFrom;
    }
    public void setHoraireArriveFrom(LocalDateTime horaireArriveFrom){
        this.horaireArriveFrom = horaireArriveFrom;
    }
    public LocalDateTime getHoraireArriveTo(){
        return this.horaireArriveTo;
    }
    public void setHoraireArriveTo(LocalDateTime horaireArriveTo){
        this.horaireArriveTo = horaireArriveTo;
    }
    public String getPlacesDisponibles(){
        return this.placesDisponibles;
    }
    public void setPlacesDisponibles(String placesDisponibles){
        this.placesDisponibles = placesDisponibles;
    }   
    public String getPlacesDisponiblesMin(){
        return this.placesDisponiblesMin;
    }
    public void setPlacesDisponiblesMin(String placesDisponiblesMin){
        this.placesDisponiblesMin = placesDisponiblesMin;
    }
    public String getPlacesDisponiblesMax(){
        return this.placesDisponiblesMax;
    }
    public void setPlacesDisponiblesMax(String placesDisponiblesMax){
        this.placesDisponiblesMax = placesDisponiblesMax;
    }

    public String getPlacesMax() {
        return placesMax;
    }

    public void setPlacesMax(String placesMax) {
        this.placesMax = placesMax;
    }

    public String getPlacesMaxMin() {
        return placesMaxMin;
    }

    public void setPlacesMaxMin(String placesMaxMin) {
        this.placesMaxMin = placesMaxMin;
    }

    public String getPlacesMaxMax() {
        return placesMaxMax;
    }

    public void setPlacesMaxMax(String placesMaxMax) {
        this.placesMaxMax = placesMaxMax;
    }

    public LocalDateTime getDateCreation(){
        return this.dateCreation;
    }
    public void setDateCreation(LocalDateTime dateCreation){
        this.dateCreation = dateCreation;
    }
    public LocalDateTime getDateCreationFrom(){
        return this.dateCreationFrom;
    }
    public void setDateCreationFrom(LocalDateTime dateCreationFrom){
        this.dateCreationFrom = dateCreationFrom;
    }
    public LocalDateTime getDateCreationTo(){
        return this.dateCreationTo;
    }
    public void setDateCreationTo(LocalDateTime dateCreationTo){
        this.dateCreationTo = dateCreationTo;
    }

    public VilleCriteria getVilleDepart(){
        return this.villeDepart;
    }

    public void setVilleDepart(VilleCriteria villeDepart){
        this.villeDepart = villeDepart;
    }
    public List<VilleCriteria> getVilleDeparts(){
        return this.villeDeparts;
    }

    public void setVilleDeparts(List<VilleCriteria> villeDeparts){
        this.villeDeparts = villeDeparts;
    }
    public VilleCriteria getVilleDestination(){
        return this.villeDestination;
    }

    public void setVilleDestination(VilleCriteria villeDestination){
        this.villeDestination = villeDestination;
    }
    public List<VilleCriteria> getVilleDestinations(){
        return this.villeDestinations;
    }

    public void setVilleDestinations(List<VilleCriteria> villeDestinations){
        this.villeDestinations = villeDestinations;
    }
    public DriverCriteria getDriver(){
        return this.driver;
    }

    public void setDriver(DriverCriteria driver){
        this.driver = driver;
    }
    public List<DriverCriteria> getDrivers(){
        return this.drivers;
    }

    public void setDrivers(List<DriverCriteria> drivers){
        this.drivers = drivers;
    }
    public VilleCriteria getLocalisationSource(){
        return this.localisationSource;
    }

    public void setLocalisationSource(VilleCriteria localisationSource){
        this.localisationSource = localisationSource;
    }
    public List<VilleCriteria> getLocalisationSources(){
        return this.localisationSources;
    }

    public void setLocalisationSources(List<VilleCriteria> localisationSources){
        this.localisationSources = localisationSources;
    }
    public VilleCriteria getLocalisationDestination(){
        return this.localisationDestination;
    }

    public void setLocalisationDestination(VilleCriteria localisationDestination){
        this.localisationDestination = localisationDestination;
    }
    public List<VilleCriteria> getLocalisationDestinations(){
        return this.localisationDestinations;
    }

    public void setLocalisationDestinations(List<VilleCriteria> localisationDestinations){
        this.localisationDestinations = localisationDestinations;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public BigDecimal getPrixMin() {
        return prixMin;
    }

    public void setPrixMin(BigDecimal prixMin) {
        this.prixMin = prixMin;
    }

    public BigDecimal getPrixMax() {
        return prixMax;
    }

    public void setPrixMax(BigDecimal prixMax) {
        this.prixMax = prixMax;
    }
}
