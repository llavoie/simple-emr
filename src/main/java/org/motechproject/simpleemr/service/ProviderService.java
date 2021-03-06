package org.motechproject.simpleemr.service;

import java.util.List;

import org.motechproject.simpleemr.domain.Provider;
import org.motechproject.simpleemr.domain.Person;

/**
 * Service interface for CRUD on simple repository providers.
 */
public interface ProviderService {

    void create(Person person);

    void add(Provider provider);

    List<Provider> findProvidersByType(String type);

    List<Provider> getProviders();

    void delete(Provider provider);

    void update(Provider provider);
}
