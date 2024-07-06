package ForMZ.Server.BookMark.Service;

import ForMZ.Server.BookMark.Entity.BookMarkPost;
import ForMZ.Server.BookMark.Repository.BookMarkPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookMarkPostService {
    private final BookMarkPostRepository bookMarkPostRepository;
    @Transactional
    public void saveAll(List<BookMarkPost> bookMarkPostList){
        bookMarkPostRepository.saveAll(bookMarkPostList);
    }
}
