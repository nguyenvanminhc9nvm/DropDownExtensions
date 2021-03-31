# DropDownExtensions
Đây là thư viện DropDown 

<img src="https://github.com/nguyenvanminhc9nvm/DropDownExtensions/blob/master/anhdemo.png" width="400" height="800" />
<img src="https://github.com/nguyenvanminhc9nvm/DropDownExtensions/blob/master/anhgifs.gif" width="400" height="800" />

Cách sử dụng 

thêm ` maven { url 'https://jitpack.io' }` 
vào gradle
```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
``` 
Thêm dependencies vào 
```
dependencies {

    ...

    implementation 'com.github.nguyenvanminhc9nvm:DropDownExtensions:dropdown'

}
```


Sử dụng 
model của bạn cần phải kế thừa từ DropDownTemplate để DropDown bắt được hiện thị và selected :
```
data class YourModel(
	val value: String
): DropDownTemplate {
	 override var textDisplay: String = value
   override var isSelected: Boolean = false
}
```

set Data tại Fragment/Activity 
```
binding.dropDownView.setListData(list)
binding.dropDownView.addOnSelectedChangeListener(object : DropDown.OnSelectedListener {
       override fun onSelected(text: String, position: Int) {
            Toast.makeText(this@MainActivity, "value selected $text", Toast.LENGTH_SHORT).show()
       }
 })
```

Các thuộc tính trong DropDown 
```
 <com.minhnv.c9nvm.dropdownextension.DropDown
        android:id="@+id/dpDemo"
        android:layout_width="200dp"
        android:layout_height="wrap_content"
        app:setBackground=""
        app:setHint=""
        app:setIconArrow=""
        app:setTextColor=""
        app:setTextDropDown=""
        app:setTextSize=""
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.554"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.946" />
```
