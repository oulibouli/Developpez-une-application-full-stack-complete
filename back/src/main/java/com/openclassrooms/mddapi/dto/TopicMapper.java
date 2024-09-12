package com.openclassrooms.mddapi.dto;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.model.Topic;

/**
 * Mapper class for converting Topic entity to TopicDTO.
 */
@Component
public class TopicMapper {

    /**
     * Converts a Topic entity to a TopicDTO.
     * 
     * @param topic the Topic entity.
     * @param subscribed the subscription status for the user.
     * @return the TopicDTO populated with data from the Topic entity.
     */
    public TopicDTO toDto(Topic topic, boolean subscribed) {
        TopicDTO topicDTO = new TopicDTO();
        topicDTO.setDescription(topic.getDescription());
        topicDTO.setId(topic.getId());
        topicDTO.setTitle(topic.getTitle());
        topicDTO.setSubscribed(subscribed);
        return topicDTO;
    }
}
