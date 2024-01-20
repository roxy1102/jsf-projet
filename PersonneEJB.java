/**
 * 
 */
package com.gestion.personne;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


/**
 * 
 */
@RequestScoped
@Named
public class PersonneEJB {
	private Personne personne;
	private List<Personne> listePersonnes;
	private Date date;
	private boolean modif =false;
	private static int perId;
	
	public PersonneEJB() {
		// TODO Auto-generated constructor stub
		personne = new Personne();
	}
	
      public Connection connect() {
    	  try {
    		  Class.forName("com.mysql.jbdc.Driver");
    		   Connection con = DriverManager.getConnection("jbdc:mysql://localhost:3306/bdpersonne", "root", "");
    		   return con;
    	  } catch (ClassNotFoundException e) {
    		  e.printStackTrace();
    		  Connection con = null;
    		  return con;
    	  }catch (SQLException e) {
    		  e.printStackTrace();
    		  Connection con = null;
    		  return con;
    	  }
      }
      
    public List<Personne> affichePersonnes(){
    	listePersonnes = new ArrayList<Personne>();
    	
    	String req = "select * from personne";
    	try {
    		PreparedStatement ps = connect().prepareStatement(req);
    		ResultSet res = ps.executeQuery();
    		
    		while(res.next()) {
    			Personne pers = new Personne();
    			pers.setId(res.getInt("id"));
    			pers.setNom(res.getString("nom"));
    			pers.setPrenom(res.getString("Prenom"));
    			pers.setDatenaiss(res.getDate("Datenaiss"));
    			listePersonnes.add(pers);
    		}
    		return listePersonnes;
    	}catch(SQLException e) {
    		e.printStackTrace();
    		return listePersonnes;
    	}
    }
    
    public void ajoutPersonne() {
    	String req ="insert into personne(nom,prenom,datenaiss)  value(?,?,?)";
    	personne.setDatenaiss(convDate(date));
    	try {
    		PreparedStatement ps = connect().prepareStatement(req);
    		ps.setString(1, personne.getNom());
    		ps.setString(2, personne.getPrenom());
    		ps.setDate(3, personne.getDatenaiss());
    		
    		ps.execute();
    		
    		affichePersonnes();
    		personne = new Personne();
    		date = null;
    	} catch(SQLException e1) {
    		e1.printStackTrace();
    	}
    }
    
    
    public void deletePersonne(Personne per) {
    	String req ="delete from personne where id =?";
    	try {
    		PreparedStatement ps = connect().prepareStatement(req);
    		ps.setInt(1, per.getId());
    		ps.execute();
    	} catch (SQLException e1) {
    		
    		e1.printStackTrace();
    	}
    }
    public void afficher(Personne per) {
    	modif=true;
    	perId= per.getId();
    	date = per.getDatenaiss();
    	personne = per;
    }
    
    public void modifPersonne() {
    	personne.setDatenaiss(convDate(date));
    	try {
    		String req = "UPDATE personne SET nom = ?, prenom = ?, datenaiss = ? WHERE id = ?";
    		PreparedStatement ps = connect().prepareStatement(req);
    		ps.setString(1, personne.getNom());
    		ps.setString(2, personne.getPrenom());
    		ps.setDate(3, personne.getDatenaiss());
    		ps.setInt(4, perId);
    		
    		System.out.println(ps);
    		
    		ps.executeUpdate();
    		
    		affichePersonnes();
    		personne = new Personne();
    		date = null;
    	}catch (SQLException e1) {
    		e1.printStackTrace();
    	}
    } 
    
   public java.sql.Date convDate(java.util.Date calendarDate) {
	   return new java.sql.Date(calendarDate.getTime());
   }
   
    
	/**
 * @return the perId
 */
public int getPerId() {
	return perId;
}

/**
 * @param perId the perId to set
 */
public void setPerId(int perId) {
	PersonneEJB.perId = perId;
}

	/**
 * @return the modif
 */
public boolean isModif() {
	return modif;
}

/**
 * @param modif the modif to set
 */
public void setModif(boolean modif) {
	this.modif = modif;
}

	/**
	 * @return the pistePersonne
	 */
	public List<Personne> getPistePersonne() {
		return affichePersonnes();
	}

	/**
	 * @param pistePersonne the pistePersonne to set
	 */
	public void setPistePersonne(List<Personne> pistePersonne) {
		
	}
	/**
	 * @return the personne
	 */
	public Personne getPersonne() {
		return personne;
	}

	/**
	 * @param personne the personne to set
	 */
	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	/**
	 * @return the listePersonnes
	 */
	public List<Personne> getListePersonnes() {
		return listePersonnes;
	}

	/**
	 * @param listePersonnes the listePersonnes to set
	 */
	public void setListePersonnes(List<Personne> listePersonnes) {
		this.listePersonnes = listePersonnes;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

      
}
