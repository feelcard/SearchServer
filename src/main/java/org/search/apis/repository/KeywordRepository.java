package org.search.apis.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.search.apis.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Optional<Keyword> findByKeywordIgnoreCase(String keyword);

    Page<Keyword> findAllByOrderByCountDesc(Pageable pageable);

}
