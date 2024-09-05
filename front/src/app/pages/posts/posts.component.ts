import { Component, OnInit } from '@angular/core';
import { PostsService } from 'src/app/core/services/posts.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {

  constructor(
    private postService: PostsService
  ) { }

  ngOnInit(): void {
    this.postService.getAllPosts().subscribe({
      next: (response) => {
        console.log(response);
        
      },
      error: (error) => {
        console.log(error);
        
      }
    })
  }

}
