package com.lianxi.dingtu.newsnfc.mvp.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.zxing.client.android.CaptureActivity2;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.newsnfc.R;
import com.lianxi.dingtu.newsnfc.app.base.NfcJellyBeanActivity;
import com.lianxi.dingtu.newsnfc.app.utils.AppConstant;
import com.lianxi.dingtu.newsnfc.app.utils.AudioUtils;
import com.lianxi.dingtu.newsnfc.app.utils.DoubleUtil;
import com.lianxi.dingtu.newsnfc.app.utils.PrinterUtils;
import com.lianxi.dingtu.newsnfc.app.utils.SpUtils;
import com.lianxi.dingtu.newsnfc.app.utils.UserInfoHelper;
import com.lianxi.dingtu.newsnfc.app.utils.card.Commands;
import com.lianxi.dingtu.newsnfc.di.component.DaggerWarenverbrauchComponent;
import com.lianxi.dingtu.newsnfc.di.module.WarenverbrauchModule;
import com.lianxi.dingtu.newsnfc.mvp.contract.WarenverbrauchContract;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardInfoBean;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardInfoTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.GetDetailList;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.GetEMGoods;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.ReadCardTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.SimpleExpenseParam;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.SimpleExpenseTo;
import com.lianxi.dingtu.newsnfc.mvp.presenter.WarenverbrauchPresenter;
import com.lianxi.dingtu.newsnfc.mvp.ui.adapter.EMDetailGoodsRvAdapter;
import com.lianxi.dingtu.newsnfc.mvp.ui.adapter.EMGoodsRvAdapter;
import com.lianxi.dingtu.newsnfc.mvp.ui.adapter.EMSelectedGoodsRvAdapter;
import com.lianxi.dingtu.newsnfc.mvp.ui.widget.PayDialog;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class WarenverbrauchActivity extends NfcJellyBeanActivity<WarenverbrauchPresenter> implements WarenverbrauchContract.View {

    @BindView(R.id.ware_type_rv)
    RecyclerView wareTypeRv;
    @BindView(R.id.ware_type_tv)
    TextView wareTypeTv;
    @BindView(R.id.ware_detail_rv)
    RecyclerView wareDetailRv;
    @BindView(R.id.ware_car)
    ImageView wareCar;
    @BindView(R.id.ware_goods_count)
    TextView wareGoodsCount;
    @BindView(R.id.ware_goods_price)
    TextView wareGoodsPrice;
    @BindView(R.id.payall)
    TextView payall;
    @BindView(R.id.ware_pb)
    ProgressBar progressBar;
    @BindView(R.id.bottomSheetLayout)
    BottomSheetLayout bottomSheetLayout;
    EMDetailGoodsRvAdapter detailAdapter;
    EMSelectedGoodsRvAdapter selectAdapter;
    private List<GetEMGoods.ContentBean> lists = new ArrayList<>();
    private List<GetDetailList.ContentBean> contentBeans = new ArrayList<>();
    private List<List<GetDetailList.ContentBean>> selectedBeans = new ArrayList<>();
    private int goodsCount = 0;
    private double goodsPrices = 0;
    private int currentItem;
    private CardInfoBean mCardInfoBean;
    int company = 0;
    int device = 0;
    private int payCount = 0;
    SimpleExpenseParam param;
    StringBuilder printer;
    ReadCardTo readCardTo;
    View bottomView;
    TextView clearBottom;
    RecyclerView bottomRv;
    private static final int SCAN_REQUEST_CODE = 6;
    private String[] payMethod = new String[]{"扫码支付", "刷卡支付"};
    private boolean isExits;
    private String pwd = "";
    private boolean canReadCard;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWarenverbrauchComponent.builder()
                .appComponent(appComponent)
                .warenverbrauchModule(new WarenverbrauchModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return R.layout.activity_warenverbrauch;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("商品消费");
        mPresenter.getEmGoods();
        company = UserInfoHelper.getInstance(this).getCode();
        String _device = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
        device = Integer.valueOf(TextUtils.isEmpty(_device) ? "1" : _device);
        bottomView = getLayoutInflater().from(this).inflate(R.layout.ware_bottom_layout, null);
        clearBottom = bottomView.findViewById(R.id.clear_bottom);
        bottomRv = bottomView.findViewById(R.id.ware_bottom_rv);
        clearBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();
                bottomSheetLayout.dismissSheet();
            }
        });
    }

    @Override
    public void onEMGoodsGet(GetEMGoods getEMGoods) {
        if (lists != null) {
            lists.clear();
        }
        lists.addAll(getEMGoods.getContent());
        initRecyclerView();
    }

    @Override
    public void onEMGoodsDetailGet(GetDetailList detailList) {
        progressBar.setVisibility(View.GONE);
        if (contentBeans != null) {
            contentBeans.clear();
        }
        contentBeans.addAll(detailList.getContent());
        // TODO: 2019/10/22 显示已选择的商品
        if (selectedBeans.size() > 0) {
            Log.d(TAG, "onEMGoodsDetailGet: asd");
            for (int i = 0; i < selectedBeans.size(); i++) {
                for (int j = 0; j < contentBeans.size(); j++) {
                    if (selectedBeans.get(i).get(0).getGoods().getGoodsName().equals(contentBeans.get(j).getGoods().getGoodsName())
                            && selectedBeans.get(i).get(0).getGoods().getPrice() == contentBeans.get(j).getGoods().getPrice()) {
                        contentBeans.get(j).setImgCount(selectedBeans.get(i).size());
                        break;
                    }
                }
            }
        }
        if (detailAdapter != null) {
            detailAdapter.notifyDataSetChanged();
        } else {
            initDetailGoodsRecyclerView();
        }
    }

    @Override
    public void onCardInfo(CardInfoTo cardInfoTo) {

    }

    @Override
    public void creatSuccess(SimpleExpenseTo simpleExpenseTo) {
        printer = new StringBuilder();
        printer.append("\n工作模式: 商品消费");
        printer.append("\n姓    名: " + readCardTo.getUserName());
        printer.append(String.format("\n折扣比率: %s", simpleExpenseTo.getExpenseDetail().getDiscountRate()));
        printer.append(String.format("\n实际消费: %.2f", simpleExpenseTo.getExpenseDetail().getAmount()));
        printer.append(String.format("\n账户余额: %.2f", simpleExpenseTo.getExpenseDetail().getBalance()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        printer.append("\n" + str);
        SpUtils.put(this, AppConstant.Print.STUB1, printer.toString());
        PrinterUtils.getInstance(WarenverbrauchActivity.this).printLianxi(printer);
        printer.setLength(0);
        payCount = -1;
        AudioUtils.getInstance().speakText("消费" + simpleExpenseTo.getExpenseDetail().getAmount() + "元");
        Toasty.success(this, "消费成功", Toast.LENGTH_SHORT, true).show();
        clearAll();
    }

    PayDialog payDialog = null;

    private void createPayDialog() {
        if (payDialog == null) {
            payDialog = new PayDialog(this);
            payDialog.setPasswordCallback(new PayDialog.PasswordCallback() {
                @Override
                public void callback(String password) {
                    pwd = password;
                    payDialog.dismiss();
                    String deviceID = (String) SpUtils.get(WarenverbrauchActivity.this, AppConstant.Receipt.NO, "");
                    param = new SimpleExpenseParam();
                    param.setNumber(mCardInfoBean.getNum());
                    param.setAmount(Double.parseDouble(wareGoodsPrice.getText().toString().trim()));
                    param.setDeviceID(Integer.valueOf(TextUtils.isEmpty(deviceID) ? "1" : deviceID));
                    param.setPayCount(payCount + 1);
                    param.setPayKey(pwd);
                    param.setPattern(4);
                    param.setDeviceType(2);
                    setGoodList();
                    mPresenter.createSimpleExpense(param);
                }
            });
        }
        payDialog.clearPasswordText();
        payDialog.setMoney(wareGoodsPrice.getText().toString().trim());
        payDialog.show();
        payDialog.setCancelable(false);
        payDialog.setCanceledOnTouchOutside(false);
    }

    private void setGoodList() {
        List<SimpleExpenseParam.ListGoodsBean> listGoodsBean = new ArrayList<>();
        for (int i = 0; i < selectedBeans.size(); i++) {
            SimpleExpenseParam.ListGoodsBean listGoods = new SimpleExpenseParam.ListGoodsBean();
            listGoods.setGoodsNo(selectedBeans.get(i).get(0).getGoods().getGoodsNo());
            listGoods.setCount(selectedBeans.get(i).size());
            listGoodsBean.add(listGoods);
        }
        param.setListGoods(listGoodsBean);
    }

    @Override
    public void creatBill(String str) {

    }

    @Override
    public void creatBill2(boolean isOpen) {
        if (goodsPrices > 0) {
            if (readCardTo == null) {
                AudioUtils.getInstance().speakText("请刷卡");
                canReadCard = true;
                return;
            }
            if (isOpen) {
                createPayDialog();
            } else {
                goToSimpleExpense();
            }
        } else {
            Toast.makeText(this, "请先选择商品", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailed() {
        ToastUtils.showShort("消费失败");
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            UserInfoHelper mUserInfoHelper = UserInfoHelper.getInstance(WarenverbrauchActivity.this);
            mCardInfoBean = Commands.getInstance(WarenverbrauchActivity.this).readTag(tag);
            Timber.wtf("M1卡信息：" + mCardInfoBean.getCode());
            if (TextUtils.isEmpty(mCardInfoBean.getCode())) {
                return;
            } else {
                if (TextUtils.equals("0000", mCardInfoBean.getCode())) {
                    onFailed();
                    AudioUtils.getInstance().speakText("空白卡");
                    showMessage("空白卡");
                    return;
                } else if (Integer.parseInt(mCardInfoBean.getCode(), 16) != mUserInfoHelper.getCode()) {
                    onFailed();
                    AudioUtils.getInstance().speakText("非本单位卡");
                    showMessage("非本单位卡");
                    return;
                }
            }
            if (canReadCard) {
                mPresenter.readtCardInfo(company, device, mCardInfoBean.getNum());
                canReadCard = false;
            }
        } else {
            System.out.println("intent null");
        }

    }

    @Override
    public void onReadCard(ReadCardTo readCardTo) {
        this.readCardTo = readCardTo;
        Toast.makeText(this, "姓名：" + readCardTo.getUserName() + "\n卡号:" + readCardTo.getNumber() + "\n消费次数:" + readCardTo.getPayCount() + "\n余额:" + readCardTo.getBalance(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() != KeyEvent.ACTION_UP) {
            //choicePay();
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_DEL && event.getAction() != KeyEvent.ACTION_UP) {
            Toast.makeText(this, "KEYCODE_DEL", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @OnClick({R.id.ware_car, R.id.payall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ware_car:
                if (selectedBeans.size() > 0) {
                    if (selectAdapter == null) {
                        initSelectedRv();
                    } else {
                        selectAdapter.notifyDataSetChanged();
                    }
                    if (bottomSheetLayout.isSheetShowing()) {
                        bottomSheetLayout.dismissSheet();
                    } else {
                        bottomSheetLayout.showWithSheetView(bottomView);
                    }
                }
                break;
            case R.id.payall:
                choicePay();
                break;
        }

    }

    private void choicePay() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("选择您的支付方式")
                .setIcon(R.mipmap.ic_launcher)
                .setItems(payMethod, new DialogInterface.OnClickListener() {//添加列表
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            if (goodsPrices > 0) {
                                startScanActivity();
                            } else {
                                Toast.makeText(WarenverbrauchActivity.this, "请先选择商品", Toast.LENGTH_SHORT).show();
                            }
                        } else if (i == 1) {
                            mPresenter.getPaySgetPayKeySwitch2();
                        }
                    }
                }).create();
        alertDialog.show();
    }

    private void initSelectedRv() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WarenverbrauchActivity.this, RecyclerView.VERTICAL, false);
        bottomRv.setLayoutManager(linearLayoutManager);
        bottomRv.addItemDecoration(new DividerItemDecoration(WarenverbrauchActivity.this, DividerItemDecoration.VERTICAL));
        selectAdapter = new EMSelectedGoodsRvAdapter(WarenverbrauchActivity.this, selectedBeans);
        bottomRv.setAdapter(selectAdapter);

        selectAdapter.setMyItemClickListener(new EMSelectedGoodsRvAdapter.OnMyItemClickListener() {
            @Override
            public void onAddClick(int position, int count) {
                selectedBeans.get(position).add(selectedBeans.get(position).get(0));
                selectAdapter.notifyDataSetChanged();
                goodsCount += 1;
                BigDecimal b1 = new BigDecimal(String.valueOf(goodsPrices));
                BigDecimal b2 = new BigDecimal(String.valueOf(selectedBeans.get(position).get(0).getGoods().getPrice()));
                goodsPrices = b1.add(b2).doubleValue();
                updateData();
                updateDetail();
            }

            @Override
            public void onReduceClick(int position, int count) {
                goodsCount -= 1;
                BigDecimal b1 = new BigDecimal(String.valueOf(goodsPrices));
                BigDecimal b2 = new BigDecimal(String.valueOf(selectedBeans.get(position).get(0).getGoods().getPrice()));
                goodsPrices = b1.subtract(b2).doubleValue();
                updateData();
                if (goodsCount == 0) {
                    wareGoodsCount.setVisibility(View.GONE);
                }
                if (count == 0) {
                    update0(position);
                }
                selectedBeans.get(position).remove(selectedBeans.get(position).get(0));
                if (selectedBeans.get(position).size() == 0) {
                    selectedBeans.remove(position);
                }
                selectAdapter.notifyDataSetChanged();
                bottomSheetLayout.peekSheet();
                if (count > 0) {
                    updateDetail();
                }
            }
        });
    }

    private void update0(int position) {
        for (int j = 0; j < contentBeans.size(); j++) {
            if (selectedBeans.get(position).get(0).getGoods().getGoodsName().equals(contentBeans.get(j).getGoods().getGoodsName())
                    && selectedBeans.get(position).get(0).getGoods().getPrice() == contentBeans.get(j).getGoods().getPrice()) {
                contentBeans.get(j).setImgCount(0);
                break;
            }
        }
        detailAdapter.notifyDataSetChanged();
    }

    private void updateDetail() {
        for (int i = 0; i < selectedBeans.size(); i++) {
            for (int j = 0; j < contentBeans.size(); j++) {
                if (selectedBeans.get(i).get(0).getGoods().getGoodsName().equals(contentBeans.get(j).getGoods().getGoodsName())
                        && selectedBeans.get(i).get(0).getGoods().getPrice() == contentBeans.get(j).getGoods().getPrice()) {
                    contentBeans.get(j).setImgCount(selectedBeans.get(i).size());
                    break;
                }
            }
        }
        detailAdapter.notifyDataSetChanged();
    }

    private void clearAll() {
        goodsCount = 0;
        goodsPrices = 0;
        wareGoodsCount.setVisibility(View.GONE);
        wareGoodsCount.setText(goodsCount + "");
        wareGoodsPrice.setText(goodsPrices + "");
        requestDetail(lists.get(currentItem).getId(), lists.get(currentItem).getName());
        readCardTo = null;
        mCardInfoBean = null;
        param = null;
        printer = null;
        selectedBeans.clear();
    }

    private void goToSimpleExpense() {
        String deviceID = (String) SpUtils.get(this, AppConstant.Receipt.NO, "");
        param = new SimpleExpenseParam();
        param.setAmount(goodsPrices);
        param.setDeviceID(Integer.valueOf(TextUtils.isEmpty(deviceID) ? "1" : deviceID));
        param.setPayCount(readCardTo.getPayCount() + 1);
        param.setPayKey("scy");
        param.setPattern(4);
        param.setDeviceType(2);
        param.setNumber(readCardTo.getNumber());
        setGoodList();
        mPresenter.createSimpleExpense(param);
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WarenverbrauchActivity.this, RecyclerView.VERTICAL, false);
        wareTypeRv.setLayoutManager(linearLayoutManager);
        wareTypeRv.addItemDecoration(new DividerItemDecoration(WarenverbrauchActivity.this, DividerItemDecoration.VERTICAL));
        EMGoodsRvAdapter adapter = new EMGoodsRvAdapter(WarenverbrauchActivity.this, lists);
        wareTypeRv.setAdapter(adapter);
        adapter.setMyItemClickListener(new EMGoodsRvAdapter.OnMyItemClickListener() {
            @Override
            public void onItemClick(String id, String name, int position) {
                requestDetail(id, name);
                currentItem = position;
                adapter.setSelectPosition(position);
                adapter.notifyDataSetChanged();
            }
        });
        requestDetail(lists.get(0).getId(), lists.get(0).getName());
    }

    private void requestDetail(String id, String name) {
        progressBar.setVisibility(View.VISIBLE);
        wareTypeTv.setText(name);
        mPresenter.getEmGoodsDetail(id);
    }

    private void initDetailGoodsRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WarenverbrauchActivity.this, RecyclerView.VERTICAL, false);
        wareDetailRv.setLayoutManager(linearLayoutManager);
        detailAdapter = new EMDetailGoodsRvAdapter(WarenverbrauchActivity.this, contentBeans);
        wareDetailRv.setAdapter(detailAdapter);
        detailAdapter.setMyItemClickListener(new EMDetailGoodsRvAdapter.OnMyItemClickListener() {
            @Override
            public void onAddClick(int position, int count) {
                goodsCount += 1;
                BigDecimal b1 = new BigDecimal(String.valueOf(goodsPrices));
                BigDecimal b2 = new BigDecimal(String.valueOf(contentBeans.get(position).getGoods().getPrice()));
                goodsPrices = b1.add(b2).doubleValue();
                updateData();
                for (int i = 0; i < selectedBeans.size(); i++) {
                    if (selectedBeans.get(i).get(0).getGoods().getGoodsName().equals(contentBeans.get(position).getGoods().getGoodsName()) && selectedBeans.get(i).get(0).getGoods().getPrice() == contentBeans.get(position).getGoods().getPrice()) {
                        selectedBeans.get(i).add(contentBeans.get(position));
                        isExits = true;
                        break;
                    }
                }
                if (!isExits) {
                    List<GetDetailList.ContentBean> c = new ArrayList<>();
                    c.add(contentBeans.get(position));
                    selectedBeans.add(c);
                }
                ToastUtils.showShort(selectedBeans.size());
                isExits = false;
            }

            @Override
            public void onReduceClick(int position, int count) {
                if (goodsCount > 0 && goodsPrices >= contentBeans.get(position).getGoods().getPrice()) {
                    goodsCount -= 1;
                    BigDecimal b1 = new BigDecimal(String.valueOf(goodsPrices));
                    BigDecimal b2 = new BigDecimal(String.valueOf(contentBeans.get(position).getGoods().getPrice()));
                    goodsPrices = b1.subtract(b2).doubleValue();
                    updateData();
                    for (int i = 0; i < selectedBeans.size(); i++) {
                        boolean isSameName = selectedBeans.get(i).get(0).getGoods().getGoodsName().equals(contentBeans.get(position).getGoods().getGoodsName());
                        boolean isSamePrice = selectedBeans.get(i).get(0).getGoods().getPrice() == contentBeans.get(position).getGoods().getPrice();
                        if (isSameName && isSamePrice) {
                            //这里注意切换界面之后 remove 的对象不是同一个了
                            selectedBeans.get(i).remove(0);
                            if (selectedBeans.get(i).size() == 0) {
                                selectedBeans.remove(i);
                            }
                            break;
                        }
                    }
                }
                if (goodsCount == 0) {
                    wareGoodsCount.setVisibility(View.GONE);
                }
            }
        });
    }

    private void updateData() {
        wareGoodsCount.setVisibility(View.VISIBLE);
        wareGoodsCount.setText(goodsCount + "");
        wareGoodsPrice.setText(goodsPrices + "");
    }

    private void startScanActivity() {
        Intent intent = new Intent(WarenverbrauchActivity.this, CaptureActivity2.class);
        intent.putExtra(CaptureActivity2.USE_DEFUALT_ISBN_ACTIVITY, true);
        startActivityForResult(intent, SCAN_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SCAN_REQUEST_CODE) {
                String isbn = data.getStringExtra("CaptureIsbn");
                if (!TextUtils.isEmpty(isbn)) {
                    Log.e(TAG, "onActivityResult: " + isbn);
                    Intent intent = new Intent();
                    intent.putExtra("content", isbn);
                    intent.putExtra("amount", goodsPrices);
                    intent.setClass(WarenverbrauchActivity.this, PaymentActivity.class);
                    clearAll();
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        goodsCount = 0;
        goodsPrices = 0;
    }
}
