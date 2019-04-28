package wang.ismy.zbq.model.vo;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.Data;
import wang.ismy.zbq.model.entity.Broadcast;
import wang.ismy.zbq.model.vo.user.UserVO;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author my
 */
@Data
public class BroadcastVO {

    private String content;

    private UserVO user;

    private Boolean self;

    @JsonAdapter(value = LocalDateAdapter.class)
    private LocalDateTime createTime;

    public static BroadcastVO convert(Broadcast broadcast,UserVO user){
        BroadcastVO vo = new BroadcastVO();

        vo.createTime = broadcast.getCreateTime();
        vo.content = broadcast.getContent();
        if (user.getUserId().equals(broadcast.getUser().getUserId())){
            vo.self = true;
        }
        vo.user = user;

        return vo;
    }

    public static BroadcastVO convert(Broadcast broadcast){
        UserVO userVO = UserVO.convert(broadcast.getUser());

        return convert(broadcast,userVO);
    }
}

class LocalDateAdapter extends TypeAdapter<LocalDateTime>{

    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        out.value(value.toString());
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        in.beginArray();
        LocalDateTime dateTime = LocalDateTime.parse(in.nextName());
        in.endObject();
        return dateTime;
    }
}