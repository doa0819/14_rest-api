package com.ohgiraffers.jpatest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MenuService(MenuRepository menuRepository, ModelMapper modelMapper){
        this.menuRepository = menuRepository;
        this.modelMapper = modelMapper;
    }

    public List<MenuDTO> findAllMenu() {

        List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());

        menuList.forEach(System.out::println);
        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .collect(Collectors.toList());
    }


    public MenuDTO findMenu(int menuCode) {

        Menu menuList = menuRepository.findById(menuCode).orElseThrow(IllegalAccessError::new);
        return modelMapper.map(menuList , MenuDTO.class);
    }
}
