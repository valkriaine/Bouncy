# Bouncy 

[ ![Download](https://api.bintray.com/packages/valkriaine/factor/bouncy/images/download.svg?version=1.5) ](https://bintray.com/valkriaine/factor/bouncy/1.5/link)

Add IOS-like overscroll animation to your scrolling views using [SpringAnimation](https://developer.android.com/guide/topics/graphics/spring-animation). 

Currently includes BouncyRecyclerView and BouncyNestedScrollView.

# Add Bouncy to your project

 In your app module build.gradle:
```
   dependencies {
        implementation 'androidx.recyclerview:recyclerview:1.1.0'
        implementation 'com.factor:bouncy:1.5'
   }
 ```
(androidx.recyclerview is required for BouncyRecyclerView to work)


# BouncyNestedScrollView

NestedScrollView with bouncy overscroll effect, achieved by overriding the default EdgeEffect

<img src="./images/BouncyNestedScrollViewDemo.gif"/> <img src="./images/FactorLauncherSettingsScreen.gif"/>

Usage:

Use as normal NestedScrollView. Place it in your layout:

```xml
<com.factor.bouncy.BouncyNestedScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:fling_animation_size=".7"
        app:overscroll_animation_size=".7">

    <LinearLayout
            android:orientation="vertical"
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
            
            ...
            ...
            ...

    </LinearLayout>

</com.factor.bouncy.BouncyNestedScrollView>
```

```fling_animation_size``` specifies the magnitude of overscroll effect for fling, default is 0.5 if no value is given. 

```overscroll_animation_size``` specifies the magnitude of overscroll effect for drag, default is 0.5 if no value is given. 

Strongly suggest to keep both values lower than 5.

# BouncyRecyclerView

BouncyRecyclerView adds overscroll effect to RecyclerView and supports drag & drop and swiping gestures

<img src="./images/BouncyRecyclerViewDemo.gif"/> <img src="./images/reorder_and_swipe.gif"/>

Usage:

Use as normal RecyclerView. Place it in your layout:

```xml
<com.factor.bouncy.BouncyRecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:recyclerview_fling_animation_size=".7"
        app:recyclerview_overscroll_animation_size=".7"
        app:allow_drag_reorder="true"
        app:allow_item_swipe="false"/>
```

set up layout manager and adapter. Theoratically supports any LayoutManager: 
```java
   recycler_view.setAdapter(myAdapter);
   recycler_view.setLayoutManager(new LinearLayoutManager(context));
   //recycler_view.setLayoutManager(new GridLayoutManager(context, 3));
```

```recyclerview_fling_animation_size``` specifies the magnitude of overscroll effect for fling, default is 0.5 if no value is given
```recyclerview_overscroll_animation_size``` specifies the magnitude of overscroll effect for drag, default is 0.5 if no value is given

```allow_drag_reorder``` and ```allow_item_swipe``` are set to false by default. If you would like to enable these features, simply set them to true.


**Drag & drop does not work out of the box. 

For drag & drop or swipe gestures to work, make your adapter extend ```BouncyRecyclerView.Adapter``` and add constructor matching parent.
(If your adapter does not extend BouncyRecyclerView.Adapter, BouncyRecyclerView will simply disable the gestures)
```java
public class MyAdapter extends BouncyRecyclerView.Adapter
{
    private final ArrayList<MyData> dataSet;

    public MyAdapter(ArrayList<MyData> dataSet)
    {
        this.dataSet = dataSet;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        MyViewHolder h = (MyViewHolder) holder;
        h.getTextView().setText(dataSet.get(position).getData());
    }

    @Override
    public int getItemCount()
    {
        return dataSet.size();
    }

    @Override
    public void onItemMoved(int fromPosition, int toPosition)
    {
        //*****must override to save changes 
        //called repeatedly when item is dragged (reordered)
        
        //example of handling reorder
        MyData item = dataSet.remove(fromPosition);
        dataSet.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemSwipedToStart(RecyclerView.ViewHolder viewHolder, int position)
    {
        //item swiped left
    }

    @Override
    public void onItemSwipedToEnd(RecyclerView.ViewHolder viewHolder, int position)
    {
        //item swiped right
    }

    @Override
    public void onItemSelected(RecyclerView.ViewHolder viewHolder)
    {
        //item long pressed (selected)
    }

    @Override
    public void onItemReleased(RecyclerView.ViewHolder viewHolder)
    {
        //item released (unselected)
    }
}
```

# My other projects

[Factor Launcher](https://github.com/Valkriaine/Factor_Launcher_Reboot) - A Windows Phone inspired launcher with some modern touch

[Mutify](https://github.com/Valkriaine/Mutify) - Automatically turn on do-not-disturb based on where you are

