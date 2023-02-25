package com.example.campin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    int images[] = {
            R.drawable.fitur1,
            R.drawable.fitur3,
            R.drawable.fitur4,
            R.drawable.fitur2
    };

    int headings[] = {
            R.string.headslide1,
            R.string.headslide2,
            R.string.headslide3,
            R.string.headslide4
    };

    int descriptions[] = {
            R.string.descslide1,
            R.string.descslide2,
            R.string.descslide3,
            R.string.descslide4
    };

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides_layout,container, false);

        // Hooks
        ImageView imageView = view.findViewById(R.id.slide_image);
        TextView head = view.findViewById(R.id.slide_head);
        TextView desc = view.findViewById(R.id.slide_desc);

        imageView.setImageResource(images[position]);
        head.setText(headings[position]);
        desc.setText(descriptions[position]);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
