# Usage

## YourItem.kt
<pre>
<code>
data class YourItem(val image:Int, val url: String)
</code>
</pre>

## Activity.kt
<pre>
<code>
    val listItem = arrayListOf(
        YourItem(R.drawable.banner_img_01, "http://google.com"),
        YourItem(R.drawable.banner_img_02, "http://google.com")
     )

    val adapter = PayssamAdBannerAdapter(activity!!, listItem)
    banner.setAdapter(adapter)
</code>
</pre>


## Adapter.kt   
<pre>
<code>
    private class RollingViewPager(context: Context, itemList: ArrayList<YourItem>) : RollingViewPagerAdapter<YourItem>(context, itemList) {

        override fun getView(position: Int, item: YourItem): View {
            val view = LayoutInflater.from(context).inflate(R.layout.item_sendbill_banner, null, false)
            val imageViewBackGround: ImageView = view.findViewById(R.id.iv_banner)

            Glide.with(imageViewBackGround)
                .load(item.image)
                .fitCenter()
                .into(imageViewBackGround)

            imageViewBackGround.setOnClickListener {
                Toast.makeText(
                    context,
                    "This is item in position $position",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(item.url)
                context.startActivity(intent)
            }

            return view
        }
    }
</code>
</pre>

## layout.xml
<pre>
<code>
        your.package.RollingView
          android:id="@+id/slider_main_banner"
          android:layout_width="match_parent"
          android:layout_height="94dp"
          android:layout_gravity="center"
          android:background="@color/transparent"
          app:bottomMargin="8dp"
          app:indicatorRes="@drawable/custom_indicator"
          app:enableLooping="true"
          app:enableIndicator="true"
          app:enableRolling="true"
          app:flingAble="true"
          app:indicatorMargin="6dp"
          app:rollingDelay="5000"
          app:scrollingDelay="600"
          app:smoothScroll="true"
</code>
</pre>
