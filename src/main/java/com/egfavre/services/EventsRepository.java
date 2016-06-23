package com.egfavre.services;

import com.egfavre.entities.Events;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by user on 6/23/16.
 */
public interface EventsRepository extends CrudRepository<Events, Integer> {
}
