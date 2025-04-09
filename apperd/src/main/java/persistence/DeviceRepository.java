package persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import model.Device;

public class DeviceRepository {
    private final String filePath = "devices.dat";
    private final List<Device> devices;

    public DeviceRepository() {
        devices = loadDevices();
    }

    // Charge la liste d'appareils depuis le fichier ou retourne une nouvelle liste
    private List<Device> loadDevices() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Device>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // Sauvegarde la liste d'appareils dans le fichier
    public void saveDevices() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(devices);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addDevice(Device device) {
        devices.add(device);
        saveDevices();
    }

    public List<Device> getAllDevices() {
        return devices;
    }

    public Device findById(String id) {
        for (Device d : devices) {
            if (d.getId().equals(id)) return d;
        }
        return null;
    }
}
