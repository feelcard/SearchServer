package org.search.apis.service;

import org.search.apis.domain.Keyword;
import org.search.apis.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KeywordService {
    private final KeywordRepository keywordRepository;

    @Autowired
    public KeywordService(KeywordRepository searchKeywordRepository) {
        this.keywordRepository = searchKeywordRepository;
    }

    public List<Keyword> getTopKeywords(int limit) {
        PageRequest pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "count"));
        Page<Keyword> topKeywords = keywordRepository.findAllByOrderByCountDesc(pageable);
        return topKeywords.getContent();
    }

    // synchronized는 성능 저하를 야기 할수 있음, ConcurrentHashMap을 사용하면 동기성 제어도 가능하고 성능적으로도 우수하나
    // 1개의 인스턴스 내에서만 사용 가능하며 어플리케이션이 종료되거나 재시작되면 데이터가 유실됨
    // 해당 로직은 별도의 캐시서버(Redis 등)를 두거나 분산형 DB(elasticSearch 등)에 저장하는 것이 좋음
    public synchronized void addSearchCount(String keyword) {
        Optional<Keyword> existingKeyword = keywordRepository.findByKeywordIgnoreCase(keyword);

        if (existingKeyword.isPresent()) {
            Keyword entity = existingKeyword.get();
            entity.setCount(entity.getCount() + 1);
            keywordRepository.save(entity);
        } else {
            keywordRepository.save(new Keyword(keyword, 1));
        }
    }
}
