package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.TopicMapper;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private TopicMapper topicMapper;

    public ResponseEntity<List<TopicDTO>> getTopics() {
        List<Topic> topics = topicRepository.findAll();

        List<TopicDTO> topicDTOs = topics.stream().map(topic -> {          
            return topicMapper.toDto(topic);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(topicDTOs);
    }
}
