package com.example;

import com.example.controller.PostController;
import com.example.model.Label;
import com.example.model.Post;
import com.example.repository.GsonLabelRepositoryImpl;
import com.example.repository.GsonPostRepositoryImpl;
import com.example.repository.LabelRepository;
import com.example.repository.PostRepository;
import com.example.view.PostView;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for Post functionality.
 */

public class PostTest {

    @Test
    public void testPositivePostCreation() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\posts1.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PostRepository pr = new GsonPostRepositoryImpl("src\\test\\resources\\posts1.json");
        PostController pc = new PostController(
                pr,
                Mockito.mock(LabelRepository.class),
                new PostView(new ByteArrayInputStream("1\nAAA\nBBB\n0\n".getBytes()), System.out));

        pc.add();
        Post post = null;
        try {
            List<Post> posts = pr.getAll();
            post = posts.get(posts.size() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("AAA", post.getTitle());
        assertEquals("BBB", post.getContent());
        assertEquals(new ArrayList<>(), post.getLabels());
    }

    @Test
    public void testPositivePostDeletion() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\posts2.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PostRepository pr = new GsonPostRepositoryImpl("src\\test\\resources\\posts2.json");
        int size = 0;
        int newSize = 0;
        try {
            pr.save(new Post(2L, "AAA", "BBB", new ArrayList<>()));
            size = pr.getAll().size();
            PostController pc = new PostController(
                    pr,
                    Mockito.mock(LabelRepository.class),
                    new PostView(
                            new ByteArrayInputStream("2\n".getBytes()),
                            System.out));
            pc.delete();
            newSize = pr.getAll().size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(size - 1, newSize);
    }

    @Test
    public void testPositivePostUpdate() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\posts3.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PostRepository pr = new GsonPostRepositoryImpl("src\\test\\resources\\posts3.json");
        String title = "";
        try {
            pr.save(new Post(1L, "AAA", "BBB", new ArrayList<>()));
            PostController pc = new PostController(
                    pr,
                    Mockito.mock(LabelRepository.class),
                    new PostView(
                            new ByteArrayInputStream("1\nCCC\nDDD\n0\n".getBytes()),
                            System.out));
            pc.update();
            title = pr.getById(1L).getTitle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("CCC", title);
    }

    @Test
    public void testNegativePostDeleteNotExist() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\posts4.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PostRepository pr = new GsonPostRepositoryImpl("src\\test\\resources\\posts4.json");
        PostController pc = new PostController(
                pr,
                Mockito.mock(LabelRepository.class),
                new PostView(
                        new ByteArrayInputStream("2\n".getBytes()),
                        System.out));
        try {
            pr.save(new Post(1L, "AAA", "BBB", new ArrayList<>()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pc.delete();
        int newSize = 0;
        try {
            newSize = pr.getAll().size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(1, newSize);
    }

    @Test
    public void testPositiveAddLabelsToPost() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\posts5.json"));
            Files.deleteIfExists(Paths.get("src\\test\\resources\\postslabels5.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PostRepository pr = new GsonPostRepositoryImpl("src\\test\\resources\\posts5.json");
        LabelRepository lr = new GsonLabelRepositoryImpl("src\\test\\resources\\postslabels5.json");

        try {
            lr.save(new Label(1L, "ABC"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        PostController pc = new PostController(pr, lr, new PostView(
                new ByteArrayInputStream("1\nAAA\nBBB\n1\n1\n".getBytes()),
                System.out));
        pc.add();

        String title = "";
        try {
            title = pr.getById(1L).getLabels().get(0).getName();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("ABC", title);
    }

    @Test
    public void testNegativePostDataIsNull() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String utf8 = StandardCharsets.UTF_8.name();
        PostRepository pr = new GsonPostRepositoryImpl("src\\test\\resources\\posts6.json");
        try (PrintStream ps = new PrintStream(baos, true, utf8)) {
            PostController pc = new PostController(
                    pr,
                    Mockito.mock(LabelRepository.class),
                    new PostView(
                            Mockito.mock(InputStream.class),
                            ps));
            pc.getAll();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Type listType = new TypeToken<List<Post>>() {
        }.getType();
        List<Post> posts;
        posts = new Gson().fromJson(baos.toString(), listType);
        if (posts == null) {
            posts = new ArrayList<>();
        }
        assertEquals(0, posts.size());
    }

    @Test
    public void testPositiveGetPostById() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\posts7.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String utf8 = StandardCharsets.UTF_8.name();
        Post post = new Post(1L, "AAA", "BBB", new ArrayList<>());
        PostRepository pr = new GsonPostRepositoryImpl("src\\test\\resources\\posts7.json");
        try (PrintStream ps = new PrintStream(baos, true, utf8)) {
            PostController pc = new PostController(
                    pr,
                    Mockito.mock(LabelRepository.class),
                    new PostView(
                            new ByteArrayInputStream("1\n".getBytes()),
                            ps));
            pr.save(post);
            pc.getOne();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type listType = new TypeToken<Post>() {
        }.getType();
        Post newPost;
        Gson gson = new Gson();
        newPost = gson.fromJson(
                JsonParser.parseString("{" + baos.toString().split("\\{")[1]).getAsJsonObject(), listType);
        assertEquals(post.getTitle(), newPost.getTitle());
    }

    @Test
    public void testNegativePostControllerAddException() {
        PostRepository mockWr = Mockito.mock(PostRepository.class);
        PostView mockWv = Mockito.mock(PostView.class);
        try {
            Mockito.when(mockWr.getNextId()).thenThrow(new IOException());
            Mockito.doReturn(1).when(mockWv).getNumber(Mockito.anyString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        PostController pc = new PostController(
                mockWr,
                Mockito.mock(LabelRepository.class),
                mockWv);
        pc.add();
        Mockito.verify(mockWv, Mockito.times(1)).showMessage(null);
    }

    @Test
    public void testNegativePostControllerUpdateException() {
        PostRepository mockWr = Mockito.mock(PostRepository.class);
        PostView mockWv = Mockito.mock(PostView.class);
        try {
            Mockito.doThrow(new IOException()).when(mockWr).update(Mockito.any(Post.class), Mockito.anyLong());
            Mockito.doReturn(1L).when(mockWv).getID();
            Mockito.doReturn(Mockito.mock(Post.class)).when(mockWv).getObject(Mockito.anyLong());
        } catch (IOException e) {
            e.printStackTrace();
        }
        PostController pc = new PostController(
                mockWr,
                Mockito.mock(LabelRepository.class),
                mockWv);
        pc.update();
        Mockito.verify(mockWv, Mockito.times(1)).showMessage(null);
    }

    @Test
    public void testNegativePostControllerDeleteException() {
        PostRepository mockWr = Mockito.mock(PostRepository.class);
        PostView mockWv = Mockito.mock(PostView.class);
        try {
            Mockito.doThrow(new IOException()).when(mockWr).delete(Mockito.anyLong());
            Mockito.doReturn(1L).when(mockWv).getID();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PostController pc = new PostController(
                mockWr,
                Mockito.mock(LabelRepository.class),
                mockWv);
        pc.delete();
        Mockito.verify(mockWv, Mockito.times(1)).showMessage(null);
    }

    @Test
    public void testNegativePostControllerGetAllException() {
        PostRepository mockWr = Mockito.mock(PostRepository.class);
        PostView mockWv = Mockito.mock(PostView.class);
        try {
            Mockito.doThrow(new IOException()).when(mockWr).getAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PostController pc = new PostController(
                mockWr,
                Mockito.mock(LabelRepository.class),
                mockWv);
        pc.getAll();
        Mockito.verify(mockWv, Mockito.times(1)).showMessage(null);
    }

    @Test
    public void testNegativePostControllerGetOneException() {
        PostRepository mockWr = Mockito.mock(PostRepository.class);
        PostView mockWv = Mockito.mock(PostView.class);
        try {
            Mockito.doThrow(new IOException()).when(mockWr).getById(Mockito.anyLong());
            Mockito.doReturn(1L).when(mockWv).getID();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PostController pc = new PostController(
                mockWr,
                Mockito.mock(LabelRepository.class),
                mockWv);
        pc.getOne();
        Mockito.verify(mockWv, Mockito.times(1)).showMessage(null);
    }

    @Test
    public void testNegativePostGetNumberViewNumberFormatException() {
        PostView wv = new PostView(
                new ByteArrayInputStream("A\n".getBytes()),
                System.out);
        int result = wv.getNumber("");
        assertEquals(0, result);
    }

    @Test
    public void testNegativePostGetNumberViewIDFormatException() {
        PostView wv = new PostView(
                new ByteArrayInputStream("A\n".getBytes()),
                System.out);
        Long result = wv.getID();
        assertEquals(null, result);
    }

    @Test
    public void testNegativePostControllerGetOneWasNotFound() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\posts8.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        PostRepository pr = new GsonPostRepositoryImpl("src\\test\\resources\\posts8.json");
        PostView mockWv = Mockito.mock(PostView.class);
        Mockito.doReturn(2L).when(mockWv).getID();

        try {
            pr.save(new Post(1L, "AAA", "BBB", new ArrayList<>()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        PostController pc = new PostController(
                pr,
                Mockito.mock(LabelRepository.class),
                mockWv);
        pc.getOne();

        Mockito.verify(mockWv, Mockito.times(1)).showMessage("Post not found.");
    }
}
