package service;

import model.Device;
import persistence.DeviceRepository;

public class DeviceService {
    private final DeviceRepository repository;

    public DeviceService(DeviceRepository repository) {
        this.repository = repository;
    }

    public boolean isDeviceVole(String id) {
        Device device = repository.findById(id);
        return device != null && device.isVole();
    }

    public void signalerVol(String id) {
        Device device = repository.findById(id);
        if (device != null && !device.isVole()) {
            device.setVole(true);
            repository.saveDevices();
        }
    }

    public void ajouterDevice(Device device) {
        repository.addDevice(device);
    }
}
