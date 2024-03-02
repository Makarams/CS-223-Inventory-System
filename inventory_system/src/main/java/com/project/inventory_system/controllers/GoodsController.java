package com.project.inventory_system.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.inventory_system.repositories.GoodsRepository;
import com.project.inventory_system.models.Goods;

import java.util.Optional;

@Controller
public class GoodsController {

    @Autowired
    private GoodsRepository goodsRepository;

    @GetMapping("/goods")
    public String getAllGoods(Model model) {
        model.addAttribute("goods", goodsRepository.findAll());
        return "goods_list";
    }

    @GetMapping("/goods/{id}")
    public String getGood(@PathVariable Long id, Model model) {
        Optional<Goods> good = goodsRepository.findById(id);
        if (good.isPresent()) {
            model.addAttribute("good", good.get());
            return "good";
        } else {
            return "redirect:/goods";
        }
    }


    @GetMapping("/goods/add")
    public String addNewGoods() {
        return "add_goods";
    }

    @PostMapping("/goods/add")
    public String addNewGoods(@RequestParam String goodsName,
                              @RequestParam String goodsDescription,
                              @RequestParam(required = false, defaultValue = "0") Integer quantity) {
        Goods goods = new Goods();
        goods.setGoodsName(goodsName);
        goods.setGoodsDescription(goodsDescription);
        goods.setQuantity(quantity);
        goodsRepository.save(goods);
        return "redirect:/goods";
    }


    @GetMapping("/goods/{id}/in")
    public String inGoods(@PathVariable Long id, Model model) {
        model.addAttribute("good", goodsRepository.findById(id).get());
        return "in_goods";
    }

    @PostMapping("/goods/{id}/in")
    public String inGoods(@PathVariable Long id,
                          @RequestParam Integer inStock) {
        Goods goods = goodsRepository.findById(id).get();
        goods.setQuantity(goods.getQuantity() + inStock);
        goodsRepository.save(goods);
        return "redirect:/goods";
    }


    @GetMapping("/goods/{id}/out")
    public String outGoods(@PathVariable Long id, Model model) {
        model.addAttribute("good", goodsRepository.findById(id).get());
        return "out_goods";
    }

    @PostMapping("/goods/{id}/out")
    public String outGoods(@PathVariable Long id,
                           @RequestParam Integer outStock) {
        Goods goods = goodsRepository.findById(id).get();
        goods.setQuantity(goods.getQuantity() - outStock);
        goodsRepository.save(goods);
        return "redirect:/goods";
    }


    @PostMapping("/goods/{id}/delete")
    public String deleteGoods(@PathVariable(value = "id") Long id,
                                Model model){
        if(!goodsRepository.existsById(id)){
            return "redirect:/goods";
        }
        Goods goods = goodsRepository.findById(id).orElseThrow();
        goodsRepository.delete(goods);
        return "redirect:/goods";
    }

}
