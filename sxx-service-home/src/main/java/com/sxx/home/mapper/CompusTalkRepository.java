package com.sxx.home.mapper;

import com.sxx.framework.domain.compus_talk.CompusTalk;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 〈一句话功能简述〉<br>
 * 〈校园宣讲管理持久层〉
 *
 * @author hyz
 * @create 2019/4/30 0030
 * @since 1.0.0
 */
public interface CompusTalkRepository extends JpaRepository<CompusTalk,String> {
}
