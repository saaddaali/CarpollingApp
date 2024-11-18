package  ma.zyn.app.dao.specification.core.paiement;

import ma.zyn.app.dao.criteria.core.paiement.CarteBancaireCriteria;
import ma.zyn.app.bean.core.paiement.CarteBancaire;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class CarteBancaireSpecification extends  AbstractSpecification<CarteBancaireCriteria, CarteBancaire>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("titulaire", criteria.getTitulaire(),criteria.getTitulaireLike());
        addPredicate("numero", criteria.getNumero(),criteria.getNumeroLike());
        addPredicate("dateExpiration", criteria.getDateExpiration(), criteria.getDateExpirationFrom(), criteria.getDateExpirationTo());
        addPredicate("codeSecret", criteria.getCodeSecret(),criteria.getCodeSecretLike());
    }

    public CarteBancaireSpecification(CarteBancaireCriteria criteria) {
        super(criteria);
    }

    public CarteBancaireSpecification(CarteBancaireCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
