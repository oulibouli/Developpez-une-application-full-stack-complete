import { Component, OnInit } from '@angular/core';
import { Topic } from 'src/app/core/models/topic.type';
import { TopicService } from 'src/app/core/services/topic.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})
export class TopicsComponent implements OnInit {
  topics: Topic[] = []
  message: string = '';
  buttonContent: string = ''
  
  constructor(
    private topicService: TopicService,
    private snackBar: MatSnackBar
  ) { }

  // Display a notification
  showNotification(message: string) {
    this.snackBar.open(message, 'Fermer', {
      duration: 3000,
    });
  }

  // Fetch all topics on initialization
  ngOnInit(): void {
    this.topicService.getTopics().subscribe({
      next: (response: Topic[]) => this.topics = response,
      error: (error) => console.log(error)
    })
  }

  // Subscribe to a topic
  subscribe(topicId: number) {
    this.topicService.subscribeTopic(topicId).subscribe({
      next: (response) => {
        const topic = this.topics.find(t => t.id === topicId);
        if (topic) {
          topic.subscribed = true;
        }
        this.showNotification(response.message);
      },
      error: (error) => {
        console.error(error)
      }
    })
  }

  // Unsubscribe a topic
  unsubscribe(topicId: number) {
    this.topicService.unsubscribeTopic(topicId).subscribe({
      next: (response) => {
        const topic = this.topics.find(t => t.id === topicId);
        if (topic) {
          topic.subscribed = false;
        }
        this.showNotification(response.message);
      },
      error: (error) => {
        console.error(error)
      }
    })
  }
}
