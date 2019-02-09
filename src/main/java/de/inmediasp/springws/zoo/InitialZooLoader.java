package de.inmediasp.springws.zoo;

import de.inmediasp.springws.zoo.buildings.Enclosure;
import de.inmediasp.springws.zoo.buildings.EnclosureRepository;
import de.inmediasp.springws.zoo.buildings.EnclosureType;
import de.inmediasp.springws.zoo.buildings.EnclosureTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitialZooLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final EnclosureRepository enclosureRepository;
    private final EnclosureTypeRepository enclosureTypeRepository;

    private boolean alreadySetup;

    @Autowired
    public InitialZooLoader(final EnclosureRepository enclosureRepository, final EnclosureTypeRepository enclosureTypeRepository) {
        this.enclosureRepository = enclosureRepository;
        this.enclosureTypeRepository = enclosureTypeRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        enclosureRepository.deleteAll();
        enclosureTypeRepository.deleteAll();

        createZoo();

        alreadySetup = true;
    }

    @Transactional
    public void createZoo() {
        createEnclosureTypes();
        createEnclosures();
    }

    @Transactional
    public void createEnclosures() {
        final Enclosure enclosure = new Enclosure();
        enclosure.setName("Affenhaus");
        enclosure.setLength(1);
        enclosure.setWidth(1);

        enclosure.setType(enclosureTypeRepository.findByValue("Cage").orElse(null));

        enclosureRepository.save(enclosure);
    }

    @Transactional
    public void createEnclosureTypes() {
        final EnclosureType cage = new EnclosureType();
        cage.setValue("Cage");

        enclosureTypeRepository.save(cage);
    }
}