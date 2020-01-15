package com.mybatisplus.generator.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LiYonghai
 * @version 1.0
 * @describe
 * @date 2020/1/7
 */
@Repository
public interface GeneratorDao {

    List<String> getDataBaseList();

}
