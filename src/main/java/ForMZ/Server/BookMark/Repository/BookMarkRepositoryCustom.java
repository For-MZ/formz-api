package ForMZ.Server.BookMark.Repository;

import ForMZ.Server.BookMark.Entity.BookMark;

import java.util.List;

public interface BookMarkRepositoryCustom {
    List<Long> findBookMarkPostId(Long BookMarkId);
    List<Long> findBookMarkHouseId(Long BookMarkId);
   BookMark UserBookMark(Long UserId);
}
