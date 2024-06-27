package ForMZ.Server.BookMark.Service;

import ForMZ.Server.BookMark.Entity.BookMarkPost;
import ForMZ.Server.BookMark.Repository.BookMarkPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookMarkPostService {
    private final BookMarkPostRepository bookMarkPostRepository;

    public void saveAll(List<BookMarkPost> bookMarkPostList){
        bookMarkPostRepository.saveAll(bookMarkPostList);
    }
}
