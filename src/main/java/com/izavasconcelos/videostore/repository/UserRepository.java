package com.izavasconcelos.videostore.repository;

import com.izavasconcelos.videostore.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {


}
