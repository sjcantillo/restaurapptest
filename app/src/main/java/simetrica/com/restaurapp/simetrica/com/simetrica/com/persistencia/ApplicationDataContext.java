package simetrica.com.restaurapp.simetrica.com.simetrica.com.persistencia;

/**
 * Created by rytscc on 14/10/2015.
 */

import android.content.Context;

import com.desandroid.framework.ada.ObjectContext;
import com.desandroid.framework.ada.ObjectSet;
import com.desandroid.framework.ada.exceptions.AdaFrameworkException;

import simetrica.com.restaurapp.simetrica.com.modelo.Restaurante;


public class ApplicationDataContext extends ObjectContext {

    public ObjectSet<Restaurante> restauntantesDAO;


    public ApplicationDataContext(Context pContext) throws AdaFrameworkException {
        super(pContext, "restaurantes.db");
        if (this.restauntantesDAO ==null)
        this.restauntantesDAO = new ObjectSet<Restaurante>(Restaurante.class, this);



    }

}
