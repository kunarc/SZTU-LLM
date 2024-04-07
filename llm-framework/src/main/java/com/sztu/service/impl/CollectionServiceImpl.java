package com.sztu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sztu.entity.Collection;
import com.sztu.mapper.CollectionMapper;
import com.sztu.service.CollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {

}
