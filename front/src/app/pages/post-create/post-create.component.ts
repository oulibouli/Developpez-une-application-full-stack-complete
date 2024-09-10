import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Topic } from 'src/app/core/models/topic.type';
import { PostsService } from 'src/app/core/services/posts.service';
import { TopicService } from 'src/app/core/services/topic.service';

@Component({
  selector: 'app-post-create',
  templateUrl: './post-create.component.html',
  styleUrls: ['./post-create.component.scss']
})
export class PostCreateComponent implements OnInit {
  createForm!: FormGroup
  topics:Topic[] = []
  message: string= '';
  messageClass: string = ''

  constructor(
    private formBuilder: FormBuilder,
    private topicService: TopicService,
    private postService: PostsService
  ) { }

  ngOnInit(): void {
    this.topicService.getTopics().subscribe({
      next:(topics: Topic[]) => this.topics = topics,
      error: (error) => console.log(error)
    })

    this.createForm = this.formBuilder.group({
      topic:['', Validators.required],
      title:['', Validators.required],
      content:['', Validators.required]
    })
  }
  onSubmit() {
    if(this.createForm.invalid) return;

    const { topic, title, content } = this.createForm.value

    this.postService.createPost({title, content}, topic).subscribe({
      next: (response) => {
        this.createForm.reset()
        this.messageClass = 'success'
        this.message = 'Article créé avec succès'
      },
      error: (error) => {
        this.messageClass = 'error'
        this.message = error
      }
    })
  }
}
