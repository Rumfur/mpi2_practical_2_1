
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.practical_2_1.R

class ImageViewer : AppCompatActivity() {
    // creating object of ViewPager
    var mViewPager: ViewPager? = null
    val arr : ArrayList<Uri> = intent.getParcelableExtra("arr")!!

    // images array
    var images = ArrayList<Int>()

    // Creating Object of ViewPagerAdapter
    private var mViewPagerAdapter: ViewPagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        for(i in arr){
            var yes: Drawable? = Drawable.createFromPath(i.path)
            images.add(Integer.parseInt(yes.toString()))
            R.drawable.ic_launcher_background
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_viewer)

        // Initializing the ViewPager Object
        mViewPager = findViewById<View>(R.id.viewPagerMain) as ViewPager

        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = ViewPagerAdapter(this@ImageViewer, images)

        // Adding the Adapter to the ViewPager
        mViewPager!!.adapter = mViewPagerAdapter
    }
}