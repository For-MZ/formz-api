package ForMZ.Server.domain.user.service;

import ForMZ.Server.domain.comment.dto.AllCommentRes;
import ForMZ.Server.domain.post.dto.AllPostRes;
import ForMZ.Server.domain.user.dto.ProfileRes;
import ForMZ.Server.domain.user.dto.ProfileUpdateReq;

public interface ProfileService {
    ProfileRes getProfile();
    ProfileRes updateProfile(ProfileUpdateReq profileUpdateReq);
    AllPostRes getPostsWrittenByUser();
    AllCommentRes getCommentsWrittenByUser();
    AllPostRes getPostsBookmarked();
}