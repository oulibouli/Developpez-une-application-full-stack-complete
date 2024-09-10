import { Component, OnInit } from '@angular/core';
import { Topic } from 'src/app/core/models/topic.type';
import { TopicService } from 'src/app/core/services/topic.service';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})
export class TopicsComponent implements OnInit {
  topics: Topic[] = []
  constructor(
    private topicService: TopicService
  ) { }

  ngOnInit(): void {
    this.topicService.getTopics().subscribe({
      next: (response: Topic[]) => this.topics = response,
      error: (error) => console.log(error)
    })
  }
  subscribe(topicId: number) {    
    this.topicService.subscribeTopic(topicId).subscribe({
      next: (response) => {
        console.log(response);
      },
      error: (error) => {
        console.error('Error :', error);
      }
  })
}
}
