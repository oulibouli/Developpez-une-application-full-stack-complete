package com.openclassrooms.mddapi.dto;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.model.Topic;

@Component
public class TopicMapper {
    public TopicDTO toDto(Topic topic) {
        TopicDTO topicDTO = new TopicDTO();
        topicDTO.setDescription(topic.getDescription());
        topicDTO.setId(topic.getId());
        topicDTO.setTitle(topic.getTitle());

        return topicDTO;
    }
}
