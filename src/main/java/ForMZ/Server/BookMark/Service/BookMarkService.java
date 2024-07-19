package ForMZ.Server.BookMark.Service;

import ForMZ.Server.BookMark.Entity.BookMark;
import ForMZ.Server.BookMark.Repository.BookMarkRepository;
import ForMZ.Server.User.Entity.User;
import ForMZ.Server.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookMarkService {
    private final BookMarkRepository bookMarkRepository;
    @Transactional
    public void save(BookMark bookMark){
        bookMarkRepository.save(bookMark);
    }
}
