package com.project.inventory_system.repositories;

import com.project.inventory_system.models.Goods;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends CrudRepository<Goods, Long> {

}
