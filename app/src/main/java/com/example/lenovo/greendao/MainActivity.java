package com.example.lenovo.greendao;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anye.greendao.gen.DaoMaster;
import com.anye.greendao.gen.DaoSession;
import com.anye.greendao.gen.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    UserDao userDao;
    private EditText etId;
    private EditText etName;
    private Button btnAdd;
    private Button btnDelete;
    private Button btnQuery;
    private TextView tvQuery;
    private String id;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDbHelp();
    }

    private void initDbHelp() {

        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(this,"recluse_db",null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daomaster=new DaoMaster(db);
        DaoSession daoSession = daomaster.newSession();
        userDao = daoSession.getUserDao();
    }

    private void initView() {
        etId = (EditText) findViewById(R.id.etId);
        etName = (EditText) findViewById(R.id.etName);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnQuery = (Button) findViewById(R.id.btnQuery);
        tvQuery = (TextView) findViewById(R.id.tvQuery);

        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        id = etId.getText().toString();
        name = etName.getText().toString();
       switch (view.getId())
         {
          case R.id.btnAdd:
              userDao.insert(new User(Long.valueOf(id), name));
              break;
           case R.id.btnDelete:
              userDao.deleteByKey(Long.valueOf(id));
              break;
            case R.id.btnQuery:
              List<User> list=userDao.loadAll();
              String userName="";
              for (int i = 0; i <list.size(); i++) {
                  userName+=list.get(i).getName()+",";
              }
              tvQuery.setText("查询全部数据======》"+userName);
              break;
      }
    }
}
