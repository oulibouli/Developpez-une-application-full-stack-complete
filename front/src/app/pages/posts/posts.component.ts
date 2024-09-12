import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from 'src/app/core/models/post.type';
import { PostsService } from 'src/app/core/services/posts.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {
  posts:Post[] = []
  isDescending: boolean = true; // Default to sort posts from recent to oldest
  arrow:string = "arrow_downward" // UI icon for sorting

  constructor(
    private postService: PostsService,
    private router: Router
  ) { }

  // Fetch and sort posts by date on component initialization
  ngOnInit(): void {
    this.postService.getAllPosts().subscribe({
      next: (response: Post[]) => {
        // Sort by date from the more recent to the oldest
        this.posts = response.sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime());
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  // Sort posts either by recent or oldest
  sortPosts(): void {
    this.posts.sort((a, b) => {
      const dateA = new Date(a.date).getTime();
      const dateB = new Date(b.date).getTime();
      return this.isDescending ? dateB - dateA : dateA - dateB;
    });
  }

  // Change the sort order
  sortOrder(): void {
    this.isDescending = !this.isDescending;
    this.sortPosts();
    this.isDescending ? this.arrow = "arrow_downward" : this.arrow = "arrow_upward"
  }

  // Open a post detail view by its ID
  openPost(postId: number) {
    this.router.navigate(['/posts', postId])
  }
}
