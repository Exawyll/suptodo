package fr.imie.suptodo.model;

public class Employe extends User {

	@Override
	public boolean isManager() {
		
		return false;
	}

}
