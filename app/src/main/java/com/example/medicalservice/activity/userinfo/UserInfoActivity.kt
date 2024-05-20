package com.example.medicalservice.activity.userinfo

import android.graphics.Color
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.configure.PickerOptions
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.blankj.utilcode.util.GsonUtils
import com.chad.library.R
import com.example.medicalservice.MyApplication
import com.example.medicalservice.baseFile.BaseActivity
import com.example.medicalservice.bean.EditUserInfoBean
import com.example.medicalservice.bean.FamilyHistoryBean
import com.example.medicalservice.bean.JsonBean
import com.example.medicalservice.bean.UserFamilyBean
import com.example.medicalservice.bean.UserInfoData
import com.example.medicalservice.bean.UserInfoEntity
import com.example.medicalservice.dataBaseBean.UserBean
import com.example.medicalservice.databinding.ActivityUserInfoBinding
import com.example.medicalservice.recycleAdapter.DeviceInfoAdapter
import com.example.medicalservice.recycleAdapter.MedicalHistoryAdapter
import com.example.medicalservice.recycleAdapter.PersonChargeAdapter
import com.example.medicalservice.recycleAdapter.UserHabitAdapter
import com.example.medicalservice.recycleAdapter.toStrList
import com.example.medicalservice.tools.API
import com.example.medicalservice.tools.GetJsonDataUtil
import com.example.medicalservice.tools.GlideUtils
import com.example.medicalservice.tools.GridSpaceItemDecoration
import com.example.medicalservice.tools.OkHttpUtil
import com.example.medicalservice.tools.confirmDialog
import com.example.medicalservice.tools.ifNull
import com.example.medicalservice.tools.log
import com.example.medicalservice.tools.toMedicalStr
import com.example.medicalservice.tools.toast
import com.example.medicalservice.tools.utils.extractBirthDate
import com.example.medicalservice.tools.utils.getTimeFormat
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.Calendar
import java.util.Date
import kotlin.concurrent.thread


/**
* @Author hxy
* Create at 2024/2/7
* @desc 用户信息页面
*/
class UserInfoActivity : BaseActivity<ActivityUserInfoBinding>() {

    private var ziliSelection = 0
    private var nurseSelection = 0
    var options1Items: List<JsonBean> = ArrayList<JsonBean>()
    val options2Items = ArrayList<ArrayList<String>>()
    val options3Items = ArrayList<ArrayList<ArrayList<String>>>()
    private var userBean: UserBean? = null

    override fun onResume() {
        super.onResume()
        initJsonData()
        getUserInfo()
    }

    private fun getUserInfo(){
        userBean = MyApplication.getInstance().db.userDao().getLoginStatusTrue(true)
        userBean?.let {
            OkHttpUtil.getInstance().doGet(API.owner(it.ownerId), object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    response.body?.let {
                        val userInfoEntity = Gson().fromJson(it.string(), UserInfoEntity::class.java)
                        if(userInfoEntity.code == 200 && userInfoEntity.data != null){
                            val userInfoData = userInfoEntity.data
                            runOnUiThread {
                                setUserBasicInfo(userInfoData)
                                initResponsiblePerson(userInfoData)
                                initMedicalHistory(userInfoData)
                            }
                        }
                    }
                }

            })
        }
    }

    /**
     * 新增
     */
    fun addUserInfo(view: View){
        OkHttpUtil.getInstance().doPost(API.addOwnerInfo,"", object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                TODO("Not yet implemented")
            }

        })
    }

    /**
     * 编辑
     */
    fun editUserInfo(view: View){
        val jsonObject = JSONObject()
        viewBinding.include1.apply {
            jsonObject.put("ownerUsername","${userNameEdittext.text}")
            jsonObject.put("ownerAccountName","${accountEdittext.text}")
            jsonObject.put("ownerName","${userNameEdittext.text}")
            jsonObject.put("ownerAge","${userAgeEdittext.text}")
            jsonObject.put("ownerSex","${if(userGenderEdittext.text.toString() == "男") "0" else "1"}")
            jsonObject.put("ownerHeight","${userHeightEdittext.text}")
            jsonObject.put("ownerWeight","${userWeightEdittext.text}")
            jsonObject.put("ownerBedNum","${userRoomIdEdittext.text}")
            jsonObject.put("ownerPhone","${userPhoneNumber.text}")
            jsonObject.put("ownerCardNumber","${userCardIdEdittext.text}")
            jsonObject.put("ownerCommunity","${organizationNameEdittext.text}")
            jsonObject.put("ownerMonthPrice","${monthPriceEdittext.text}")
            jsonObject.put("ownerArea","${shengshiquEdittext.text}")
            jsonObject.put("ownerBuilding","${buildingEdittext.text}")
            jsonObject.put("ownerUnit","${unitEdittext.text}")
            jsonObject.put("ownerFloor","${floorEdittext.text}")
            jsonObject.put("ownerRoomNum","${userRoomIdEdittext.text}")
            jsonObject.put("ownerCarNumber","${carIdEdittext.text}")
            jsonObject.put("ownerRole","$currentPortraits")
            var ownerSelfAssess = ""
            if(ziliSelection != 0){
                ownerSelfAssess = "${ziliSelection - 1}"
            }
            jsonObject.put("ownerSelfAssess",ownerSelfAssess)
            if(nurseSelection != 0){
                jsonObject.put("ownerNurseAssess",nurseSelection)
            }
            val jsonArray = JSONArray()
            list.forEachIndexed { index, personChargeBean ->
                when(index){
                    0,1->{
                        val innerObject = JSONObject()
                        innerObject.put("familyMemberId","${personChargeBean.id}")
                        innerObject.put("familyMemberName","${personChargeBean.name}")
                        innerObject.put("familyMemberSex","${personChargeBean.sex}")
                        innerObject.put("familyMemberRelation","${personChargeBean.title}")
                        innerObject.put("familyMemberPhone","${personChargeBean.phone}")
                        innerObject.put("familyMemberEcontact","${personChargeBean.eContact}")
                        jsonArray.put(innerObject)
                    }
                    2->{
                        jsonObject.put("ownerNurse",personChargeBean.name)
                        jsonObject.put("ownerNurseId",personChargeBean.id)
                        jsonObject.put("ownerNursePhone",personChargeBean.phone)
                    }
                    3->{
                        jsonObject.put("ownerNurseTow",personChargeBean.name)
                        jsonObject.put("ownerNurseIdTow",personChargeBean.id)
                        jsonObject.put("ownerNursePhoneTow",personChargeBean.phone)
                    }
                    4->{
                        jsonObject.put("ownerSupervisorDoctorName",personChargeBean.name)
                        jsonObject.put("ownerSupervisorDoctorPhone",personChargeBean.phone)
                    }
                    5->{
                        jsonObject.put("ownerManagerName",personChargeBean.name)
                        jsonObject.put("ownerManagerPhone",personChargeBean.phone)
                    }
                }

            }

            jsonObject.put("oOwnerFamilyMembers",jsonArray)
            jsonObject.put("father",father.toStrList().joinToString(","))
            jsonObject.put("mother",mother.toStrList().joinToString(","))
            jsonObject.put("children",children.toStrList().joinToString(","))
            jsonObject.put("sibling",sibling.toStrList().joinToString(","))
        }


        OkHttpUtil.getInstance().doPost(API.editOwnerInfo,jsonObject.toString(), object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {

            }

        })
    }

    override fun initData() {
        super.initData()
        initDeviceList()
    }

    /**
     * 个人病史，家族病史
     */
    private var father = mutableListOf<MedicalHistoryAdapter.MedicalHisBean>()
    private var mother = mutableListOf<MedicalHistoryAdapter.MedicalHisBean>()
    private var children = mutableListOf<MedicalHistoryAdapter.MedicalHisBean>()
    private var sibling = mutableListOf<MedicalHistoryAdapter.MedicalHisBean>()
    private fun initMedicalHistory(userInfoData: UserInfoData) =runOnUiThread{
        userInfoData.ownerIllness?.let {
            val ownerIllness = mutableListOf<MedicalHistoryAdapter.MedicalHisBean>()
            val split = it.toString().split(",")
            split.forEach {
                when(it){
                    "00"->{
                        ownerIllness.add(MedicalHistoryAdapter.MedicalHisBean("高血压"))
                    }
                    "01"->{
                        ownerIllness.add(MedicalHistoryAdapter.MedicalHisBean("糖尿病"))
                    }
                    "02"->{
                        ownerIllness.add(MedicalHistoryAdapter.MedicalHisBean("高血脂"))
                    }
                    "03"->{
                        ownerIllness.add(MedicalHistoryAdapter.MedicalHisBean("冠心病"))
                    }
                    "04"->{
                        ownerIllness.add(MedicalHistoryAdapter.MedicalHisBean("脑卒中"))
                    }
                    "05"->{
                        ownerIllness.add(MedicalHistoryAdapter.MedicalHisBean("慢性肺病"))
                    }
                    "06"->{
                        ownerIllness.add(MedicalHistoryAdapter.MedicalHisBean("癌症"))
                    }
                    "07"->{
                        ownerIllness.add(MedicalHistoryAdapter.MedicalHisBean("阿兹海默症"))
                    }
                }
            }
            ownerIllness.add(MedicalHistoryAdapter.MedicalHisBean())
            ownerIllness.add(MedicalHistoryAdapter.MedicalHisBean())
            val medicalHistoryAdapter = MedicalHistoryAdapter()
            medicalHistoryAdapter.setData(ownerIllness,"0")
            viewBinding.include4.personalMedicalHistoryRv.apply {
                layoutManager = FlexboxLayoutManager(this@UserInfoActivity,FlexDirection.ROW)
                adapter = medicalHistoryAdapter
            }
            medicalHistoryAdapter.OnMedicalHistoryDelete {
                ownerIllness.removeAt(it)
                medicalHistoryAdapter.setData(ownerIllness,"0")
            }
            medicalHistoryAdapter.OnMedicalHistoryEdit {
                ownerIllness.forEach {
                    it.deleteIsShow = !it.deleteIsShow
                }
                medicalHistoryAdapter.setData(ownerIllness,"0")
            }
            medicalHistoryAdapter.OnMedicalHistoryAdd {
                this.confirmDialog {
                    ownerIllness.add(ownerIllness.size - 2, MedicalHistoryAdapter.MedicalHisBean(it.toMedicalStr()))
                    medicalHistoryAdapter.setData(ownerIllness,"0")
                }
            }
        }
        OkHttpUtil.getInstance().doPost(API.medicalHistory,"", object : Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body
                body?.let {
                    val string = it.string()
                    val familyHistoryBean = Gson().fromJson(string, FamilyHistoryBean::class.java)
                    val rows = familyHistoryBean.rows
                    if(rows.isNotEmpty()){
                        val first = rows.first()
                        first.father?.let {
                            father = it.split(",").toMutableList().map {
                                MedicalHistoryAdapter.MedicalHisBean(it)
                            }.toMutableList()
                        }
                        father.add(MedicalHistoryAdapter.MedicalHisBean("编辑"))
                        father.add(MedicalHistoryAdapter.MedicalHisBean("添加"))
                        first.mother?.let {
                            mother = it.split(",").toMutableList().map {
                                MedicalHistoryAdapter.MedicalHisBean(it)
                            }.toMutableList()
                        }
                        mother.add(MedicalHistoryAdapter.MedicalHisBean("编辑"))
                        mother.add(MedicalHistoryAdapter.MedicalHisBean("添加"))
                        first.children?.let {
                            children = it.split(",").toMutableList().map {
                                MedicalHistoryAdapter.MedicalHisBean(it)
                            }.toMutableList()
                        }
                        children.add(MedicalHistoryAdapter.MedicalHisBean("编辑"))
                        children.add(MedicalHistoryAdapter.MedicalHisBean("添加"))
                        first.sibling?.let {
                            sibling = it.split(",").toMutableList().map {
                                MedicalHistoryAdapter.MedicalHisBean(it)
                            }.toMutableList()
                        }
                        sibling.add(MedicalHistoryAdapter.MedicalHisBean("编辑"))
                        sibling.add(MedicalHistoryAdapter.MedicalHisBean("添加"))
                        runOnUiThread {
                            //father
                            val fatherHistoryAdapter = MedicalHistoryAdapter()
                            fatherHistoryAdapter.OnMedicalHistoryDelete {
                                father.removeAt(it)
                                fatherHistoryAdapter.setData(father,"1")
                            }
                            fatherHistoryAdapter.OnMedicalHistoryEdit {
                                father.forEach {
                                    it.deleteIsShow = !it.deleteIsShow
                                }
                                fatherHistoryAdapter.setData(father,"1")
                            }
                            fatherHistoryAdapter.OnMedicalHistoryAdd {
                                this@UserInfoActivity.confirmDialog {
                                    father.add(father.size - 2, MedicalHistoryAdapter.MedicalHisBean(it.toMedicalStr()))
                                    fatherHistoryAdapter.setData(father,"1")
                                }
                            }
                            fatherHistoryAdapter.setData(father.toMutableList(),"1")
                            viewBinding.include4.fatherMedicalHistoryRv.apply {
                                layoutManager = FlexboxLayoutManager(this@UserInfoActivity,FlexDirection.ROW)
                                adapter = fatherHistoryAdapter
                            }
                            //mother
                            val motherHistoryAdapter = MedicalHistoryAdapter()
                            motherHistoryAdapter.OnMedicalHistoryDelete {
                                mother.removeAt(it)
                                motherHistoryAdapter.setData(mother,"1")
                            }
                            motherHistoryAdapter.OnMedicalHistoryEdit {
                                mother.forEach {
                                    it.deleteIsShow = !it.deleteIsShow
                                }
                                motherHistoryAdapter.setData(mother,"1")
                            }
                            motherHistoryAdapter.OnMedicalHistoryAdd {
                                this@UserInfoActivity.confirmDialog {
                                    mother.add(mother.size - 2, MedicalHistoryAdapter.MedicalHisBean(it.toMedicalStr()))
                                    motherHistoryAdapter.setData(mother,"1")
                                }
                            }
                            motherHistoryAdapter.setData(mother.toMutableList(),"1")

                            viewBinding.include4.motherMedicalHistoryRv.apply {
                                layoutManager = FlexboxLayoutManager(this@UserInfoActivity,FlexDirection.ROW)
                                adapter = motherHistoryAdapter
                            }
                            //children
                            val childrenHistoryAdapter = MedicalHistoryAdapter()
                            childrenHistoryAdapter.OnMedicalHistoryDelete {
                                children.removeAt(it)
                                childrenHistoryAdapter.setData(children,"1")
                            }
                            childrenHistoryAdapter.OnMedicalHistoryEdit {
                                children.forEach {
                                    it.deleteIsShow = !it.deleteIsShow
                                }
                                childrenHistoryAdapter.setData(children,"1")
                            }
                            childrenHistoryAdapter.OnMedicalHistoryAdd {
                                this@UserInfoActivity.confirmDialog {
                                    children.add(children.size - 2, MedicalHistoryAdapter.MedicalHisBean(it.toMedicalStr()))
                                    childrenHistoryAdapter.setData(children,"1")
                                }
                            }
                            childrenHistoryAdapter.setData(children.toMutableList(),"1")
                            viewBinding.include4.childrenMedicalHistoryRv.apply {
                                layoutManager = FlexboxLayoutManager(this@UserInfoActivity,FlexDirection.ROW)
                                adapter = childrenHistoryAdapter
                            }
                            //sibling
                            val siblingHistoryAdapter = MedicalHistoryAdapter()
                            siblingHistoryAdapter.OnMedicalHistoryDelete {
                                sibling.removeAt(it)
                                siblingHistoryAdapter.setData(sibling,"1")
                            }
                            siblingHistoryAdapter.OnMedicalHistoryEdit {
                                sibling.forEach {
                                    it.deleteIsShow = !it.deleteIsShow
                                }
                                siblingHistoryAdapter.setData(sibling,"1")
                            }
                            siblingHistoryAdapter.OnMedicalHistoryAdd {
                                this@UserInfoActivity.confirmDialog {
                                    sibling.add(sibling.size - 2, MedicalHistoryAdapter.MedicalHisBean(it.toMedicalStr()))
                                    siblingHistoryAdapter.setData(sibling,"1")
                                }
                            }
                            siblingHistoryAdapter.setData(sibling.toMutableList(),"1")
                            viewBinding.include4.childrenTextMedicalHistoryRv.apply {
                                layoutManager = FlexboxLayoutManager(this@UserInfoActivity,FlexDirection.ROW)
                                adapter = siblingHistoryAdapter
                            }
                        }
                    }
                }
            }

        })





    }

    private fun initDeviceList() {
        val list = mutableListOf<Any>()
        val myAdapter = DeviceInfoAdapter()
        repeat(6){
            list.add(Any())
        }
        myAdapter.setData(list)
        viewBinding.include3.deviceRv.apply {
            layoutManager = GridLayoutManager(this@UserInfoActivity,3)
            addItemDecoration(GridSpaceItemDecoration(30,10))
            adapter = myAdapter
        }
    }

    /**
     * 责任人
     */
    val list = mutableListOf<PersonChargeAdapter.PersonChargeBean>()
    val myAdapter = PersonChargeAdapter()
    private fun initResponsiblePerson(userInfoData: UserInfoData) {

        OkHttpUtil.getInstance().doPost(API.requestFamily,"", object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body
                body?.let {
                    val string = it.string()
                    val familyBean = Gson().fromJson(string, UserFamilyBean::class.java)
                    val data = familyBean.data
                    data?.forEachIndexed { index, dataXX ->
                        if(index <= 1){
                            list.add(PersonChargeAdapter.PersonChargeBean("家属",dataXX.familyMemberName,dataXX.familyMemberPhone,dataXX.familyMemberId.toString(),dataXX.familyMemberSex,dataXX.familyMemberEcontact))
                        }
                    }
                    runOnUiThread {
                        list.add(PersonChargeAdapter.PersonChargeBean("主督护工",userInfoData.ownerNurse.toString(),userInfoData.ownerNursePhone.toString(),userInfoData.ownerNurseId.toString()))
                        list.add(PersonChargeAdapter.PersonChargeBean("主督护士",userInfoData.ownerNurseTow.toString(),userInfoData.ownerNursePhoneTow.toString(),userInfoData.ownerNurseIdTow.toString()))
                        list.add(PersonChargeAdapter.PersonChargeBean("主管医生",userInfoData.ownerSupervisorDoctorName.toString(),userInfoData.ownerSupervisorDoctorPhone.toString()))
                        list.add(PersonChargeAdapter.PersonChargeBean("主管领导",userInfoData.ownerManagerName.toString(),userInfoData.ownerManagerPhone.toString()))
                        myAdapter.setData(list)
                        viewBinding.include2.personChargeRv.apply {
                            layoutManager = GridLayoutManager(this@UserInfoActivity,2)
                            adapter = myAdapter
                        }
                        myAdapter.OnRelationTextChangeCallback { position,s ->
                            list[position].apply {
                                name = s
                            }
                        }
                        myAdapter.OnPhoneTextChangeCallback { position,s ->
                            list[position].apply {
                                phone = s
                            }
                        }
                    }
                }
            }

        })



    }

    /**
     * 用户行为习惯
     */
    private fun initUserHabit(data: UserInfoData) {
        viewBinding.include1.userHabitEdit.setText("${data.ownerBehavior}")
        val list = mutableListOf("${data.ownerBehavior}")
        val myAdapter =  UserHabitAdapter()
        viewBinding.include1.userHabitRv.apply {
            layoutManager = FlexboxLayoutManager(this@UserInfoActivity,FlexDirection.ROW)
            adapter = myAdapter
        }
        myAdapter.setData(list)
    }


    /**
     * 用户画像
     */
    private val userPortraits = mutableListOf("普通职工","政府领导","事业单位","公务员","企业家","退休职工","低保老人")
    private var currentPortraits = ""
    private fun initUserPortrait(data: UserInfoData) {
        val userPortraitsAdapter = ArrayAdapter(this@UserInfoActivity,com.example.medicalservice.R.layout.spinner_dropdown_item,userPortraits)
        val i = data.ownerRole?.ifNull { "0" }?.toInt()
        currentPortraits = data.ownerRole?.ifNull { "" }.toString()
        viewBinding.include1.userPortraitSpinner.apply {
            adapter = userPortraitsAdapter
            this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    currentPortraits = "$position"
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
            setSelection(i!!)
        }

        val role = when(data.ownerRole.ifNull { "" }){
            "00"->{
                "普通职工"
            }
            "01"->{
                "政府领导"
            }
            "02"->{
                "事业单位"
            }
            "03"->{
                "公务员"
            }
            "04"->{
                "企业家"
            }
            "05"->{
                "退休职工"
            }
            "06"->{
                "退役军官"
            }
            "07"->{
                "低保老人"
            }
            else->{
                "其他"
            }
        }
//        val list = mutableListOf(role)
//        val myAdapter =  UserHabitAdapter()
//        myAdapter.setData(list)
//
//        viewBinding.include1.userPortraitRv.apply {
//            layoutManager = FlexboxLayoutManager(this@UserInfoActivity,FlexDirection.ROW)
//            adapter = myAdapter
//        }
    }

    /**
     * 初始化用户基本信息
     */
    private fun setUserBasicInfo(userInfoData: UserInfoData) {
        fun getSelfAssess(data : String):String{
           return when(data){
                "0"->{
                    "自理"
                }
                "1"->{
                    "一级"
                }
                "2"->{
                    "二级"
                }
                "3"->{
                    "三级"
                }
                "4"->{
                    "四级"
                }

               else -> {
                   ""
               }
           }
        }

        viewBinding.include1.apply {
            userInfoData.ownerPic?.let {pic->
                userAvatarImage.visibility = View.VISIBLE
                userAddAvatarLayout.visibility = View.INVISIBLE
                GlideUtils.load(this@UserInfoActivity,pic.toString(),userAvatarImage)
            }?: kotlin.run {
                userAvatarImage.visibility = View.INVISIBLE
                userAddAvatarLayout.visibility = View.VISIBLE
            }
            remainMoneyText.text = "￥：${userInfoData.ownerRemainMoney}"
            userNameEdittext.setText("${userInfoData.ownerName.ifNull { "" }}")
            userAgeEdittext.setText("${userInfoData.ownerAge}")
            userGenderEdittext.setText("${if(userInfoData.ownerSex.toString() == "0") "男" else "女"}")
            userRoomIdEdittext.setText("${userInfoData.ownerBedNum}")
            userCardIdEdittext.setText("${userInfoData.ownerCardNumber}")
            userBirthdayEdittext.setText("${userInfoData.ownerCardNumber?.extractBirthDate()}")
            userLiveTimeEdittext.setText("${userInfoData.createTime}")
            organizationNameEdittext.setText("${userInfoData.ownerCommunity}")
            userPhoneNumber.setText(userInfoData.ownerPhone)
            buildingEdittext.setText(userInfoData.ownerBuilding)
            unitEdittext.setText(userInfoData.ownerUnit)
            floorEdittext.setText(userInfoData.ownerFloor)
            roomIdEdittext.setText(userInfoData.ownerRoomNum)
            carIdEdittext.setText(userInfoData.ownerCarNumber.toString())
            accountEdittext.setText(userInfoData.ownerAccountName.toString())
            userHeightEdittext.setText(userInfoData.ownerHeight.toString())
            userWeightEdittext.setText(userInfoData.ownerWeight.toString())
            val selfAssess = getSelfAssess(userInfoData.ownerSelfAssess.toString())
            ziliEdittext.setText(selfAssess)
//            ziliSpinnerList.forEachIndexed loop@{ index, s ->
//                if(selfAssess == s){
//                    ziliSelection = index
//                    return@loop
//                }
//            }
            //ziliSpinner.setSelection(ziliSelection)
            //ziliEdittext.setText("${getSelfAssess(userInfoData.ownerSelfAssess.toString())}")
//            val ownerNurseAdapter = ArrayAdapter(this@UserInfoActivity,com.example.medicalservice.R.layout.spinner_dropdown_item,ownerNurseSpinnerList)
//            nurseLevelSpinner.adapter = ownerNurseAdapter
//            ownerNurseSpinnerList.forEachIndexed loop@{ index, s ->
//                if(userInfoData.ownerNurseAssess.ifNull { "" } == s){
//                    nurseSelection = index
//                    return@loop
//                }
//            }
            //nurseLevelSpinner.setSelection(nurseSelection)
            nurseLevelEdittext.text = "${userInfoData.ownerNurseAssess.ifNull { "" }}"
            monthPriceEdittext.text = "${userInfoData.ownerMonthPrice}"
            shengshiquEdittext.text = "${userInfoData.ownerArea}"
            initUserPortrait(userInfoData)
            initUserHabit(userInfoData)
            userBirthdayEdittext.setOnClickListener {
                val pvTimer = TimePickerBuilder(this@UserInfoActivity
                ) { date, v -> userBirthdayEdittext.text = date?.getTimeFormat() }.setItemVisibleCount(7).isAlphaGradient(true).setLineSpacingMultiplier(2.0f).build()
                pvTimer.setDate(Calendar.getInstance())
                pvTimer.show()
            }
            shengshiquEdittext.setOnClickListener {
                val pvOptions2 = object : OptionsPickerBuilder(this@UserInfoActivity,
                    object : OnOptionsSelectListener {
                        override fun onOptionsSelect(
                            options1: Int,
                            options2: Int,
                            options3: Int,
                            v: View?,
                        ) {
                            val opt1tx = if (options1Items.isNotEmpty()) options1Items[options1].pickerViewText else ""
                            val opt2tx = if (options2Items.size > 0
                                && options2Items[options1].size > 0
                            ) options2Items[options1][options2] else ""
                            val opt3tx =
                                if (options2Items.size > 0 && options3Items[options1].size > 0 && options3Items[options1][options2].size > 0) options3Items[options1][options2][options3] else ""
                            val tx = opt1tx + opt2tx + opt3tx
                            shengshiquEdittext.text = tx
                            Toast.makeText(this@UserInfoActivity, tx, Toast.LENGTH_SHORT).show()
                        }

                    }) {}.setTitleText("省市区选择")
                        .setDividerColor(Color.BLACK)
                    .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                    .setContentTextSize(20)
                    .build<Any>()

                pvOptions2.setPicker(options1Items,
                    options2Items as List<MutableList<Any>>?, options3Items as List<MutableList<MutableList<Any>>>?
                ) //三级选择器
                pvOptions2.show()
            }
        }
    }


    /**
     * 解析省市区数据
     */
    private fun initJsonData()= thread{
        val JsonData = GetJsonDataUtil().getJson(this, "province.json") //获取assets目录下的json文件数据


        val jsonBean: ArrayList<JsonBean> = parseData(JsonData)!! //用Gson 转成实体


        options1Items = jsonBean

        for (i in jsonBean.indices) { //遍历省份
            val cityList = ArrayList<String>() //该省的城市列表（第二级）
            val province_AreaList = ArrayList<ArrayList<String>>() //该省的所有地区列表（第三极）
            for (c in jsonBean[i].cityList.indices) { //遍历该省份的所有城市
                val cityName = jsonBean[i].cityList[c].name
                cityList.add(cityName) //添加城市
                val city_AreaList = ArrayList<String>() //该城市的所有地区列表

                city_AreaList.addAll(jsonBean[i].cityList[c].area)
                province_AreaList.add(city_AreaList) //添加该省所有地区数据
            }

            options2Items.add(cityList)

            options3Items.add(province_AreaList)
        }
    }

    private fun parseData(result: String?): ArrayList<JsonBean>{ //Gson 解析
        val detail = ArrayList<JsonBean>()
        try {
            val data = JSONArray(result)
            val gson = Gson()
            for (i in 0 until data.length()) {
                val entity = gson.fromJson(
                    data.optJSONObject(i).toString(),
                    JsonBean::class.java
                )
                detail.add(entity)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return detail
    }

}