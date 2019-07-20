package com.example.a33206.wechange.Action;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a33206.wechange.Adapt.ActivityAdapt;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.db.Action;
import com.example.a33206.wechange.db.User;

import java.util.ArrayList;
import java.util.List;

public class ActionFragment extends Fragment {
    private RecyclerView recyclerView;
    private ActivityAdapt activityAdapt;
    private List<User> userList= new ArrayList<>();
    private List<Action> actionList= new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_action,container,false);
        recyclerView=view.findViewById(R.id.action_reyecler);
        initList();
        activityAdapt=new ActivityAdapt(userList,actionList,getActivity(),R.drawable.logo);
        StaggeredGridLayoutManager layoutManager =new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(activityAdapt);
        return view;
    }

    private void initList() {
        User user = new User();
        user.setUserName("帅逼");
        user.setUserId("2019-7-20");
//        user.setUseIconUrl();

        Action action = new Action();
        action.setActivityName("学生会部长换届");
        action.setActivityDetail("1、选拔结束后将最终确定的名单进行公示，公示期不少于三个工作日，期间对名单有任何异议，可以进行投诉（投诉电话：18571805459，投诉邮箱：2274019789@qq.com）。\n" +
                "2、遵循考察任用制度：选拔结束后由院内老师及主席团成员对拟任用人选进行考察，考察期至同年九月底，如合格则正式录用，如不合格则另行安排。\n" +
                "3、完成工作交接：上届部长级成员传授工作中的经验教训及工作方法并在实际工作开展中给予新一届部长级成员支持与帮助。\n" +
                "4、笔试主要测试报名同学对竞职岗位和学生组织章程的了解程度、统筹能力、细节思考能力等。\n" +
                "5、答辩主要体现竞选同学对于竞选职位的理解以及工作规划，同时还考验竞选同学的语言表达能力和应变能力。演讲内容应至少包括自我介绍、职务理解、个人工作陈述等方面，评委将主要从演讲态度、演讲内容、演讲能力和问题答辩情况这四个方面进行打分。");
        action.setActivityNeedPeopleNumber(10);
        action.setActivityJoinPeoleNumber(9);
        action.setStartTime("2019-3-11");
        action.setEndTime("3-15");
        for (int i=0;i<4;i++) {
            userList.add(user);
            actionList.add(action);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
