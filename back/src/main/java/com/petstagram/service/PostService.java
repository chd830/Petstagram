package com.petstagram.service;

import com.petstagram.data.Posts;

import java.util.List;

public interface PostService {
    public List<Posts> getAll();
    public Posts getBypostNo(int postNo);
    public boolean insert(Posts posts);
    public boolean update(Posts posts);
    public boolean deleteBypostNo(int PostNo);
}
