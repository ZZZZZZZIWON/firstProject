package com.example.firstproject.repository;

import com.example.firstproject.entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {

    @Override
    ArrayList<Member> findAll();
}
