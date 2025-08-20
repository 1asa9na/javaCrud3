package com.example.service.impl;

import java.util.List;
import com.example.model.Post;
import com.example.model.Writer;
import com.example.repository.WriterRepository;
import com.example.service.WriterService;

public class WriterServiceImpl extends WriterService {

    public WriterServiceImpl(WriterRepository repository) {
        super(repository);
    }

    @Override
    public Writer getById(Long id) throws Exception {
        Writer p = repository.getById(id);
        p.setPosts(repository.getAllPostsByWriterId(id));
        return p;
    }

    @Override
    public List<Writer> getAll() throws Exception {
        List<Writer> writers = repository.getAll();
        for (int i = 0; i < writers.size(); i++) {
            Writer p = writers.get(i);
            List<Post> posts = repository.getAllPostsByWriterId(p.getId());
            p.setPosts(posts);
            writers.set(i, p);
        }
        return writers;
    }

    @Override
    public Writer save(Writer entity) throws Exception {
        Writer p = repository.save(entity);
        return p;
    }

    @Override
    public Writer update(Writer entity) throws Exception {
        Writer p = repository.update(entity);
        p.setPosts(repository.getAllPostsByWriterId(p.getId()));
        return p;
    }

    @Override
    public void delete(Long id) throws Exception {
        repository.deleteById(id);
    }
}
