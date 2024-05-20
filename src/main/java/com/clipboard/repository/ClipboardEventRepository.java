package com.clipboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clipboard.entity.ClipboardEventEntity;

@Repository
public interface ClipboardEventRepository extends JpaRepository<ClipboardEventEntity, Long> {

	 ClipboardEventEntity save(ClipboardEventEntity clipboardEvent);

}
