package ForMZ.Server.BookMark.Service;

import ForMZ.Server.BookMark.Entity.BookMark;
import ForMZ.Server.BookMark.Repository.BookMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookMarkService {
    private final BookMarkRepository bookMarkRepository;

    public void save(BookMark bookMark){
        bookMarkRepository.save(bookMark);
    }

}
