package model;

import java.io.Serializable;

public class Device implements Serializable {
    private final String id;          // Identifiant unique (IMEI, numéro, etc.)
    private final String type;        // Type d'appareil (Téléphone, Laptop, etc.)
    private final String marque;      // Marque de l'appareil
    private final String ownerEmail;  // Email du propriétaire
    private boolean vole;       // Statut de vol

    public Device(String id, String type, String marque, String ownerEmail) {
        this.id = id;
        this.type = type;
        this.marque = marque;
        this.ownerEmail = ownerEmail;
        this.vole = false;
    }

    // Getters
    public String getId() { return id; }
    public String getType() { return type; }
    public String getMarque() { return marque; }
    public String getOwnerEmail() { return ownerEmail; }
    public boolean isVole() { return vole; }

    // Setter pour le statut de vol
    public void setVole(boolean vole) { this.vole = vole; }

    @Override
    public String toString() {
        return type + " " + marque + " (" + id + ") - " + (vole ? "VOLÉ" : "OK");
    }
}
