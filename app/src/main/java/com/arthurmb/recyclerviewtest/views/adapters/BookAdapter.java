package com.arthurmb.recyclerviewtest.views.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.arthurmb.recyclerviewtest.OnItemClickListener;
import com.arthurmb.recyclerviewtest.R;
import com.arthurmb.recyclerviewtest.models.Book;
import com.arthurmb.recyclerviewtest.views.Utils;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.ImageViewBitmapInfo;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by saulmm on 08/12/14.
 */
public class BookAdapter extends RecyclerView.Adapter<BooksViewHolder> {

    private final ArrayList<Book> books;
    private Context context;
    private int defaultBackgroundcolor;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public BookAdapter(ArrayList<Book> books) {

        this.books = books;

    }

    @Override
    public BooksViewHolder onCreateViewHolder(ViewGroup viewGroup,  int position) {

        View rowView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_book, viewGroup, false);

        this.context = viewGroup.getContext();
        defaultBackgroundcolor = context.getResources().getColor(R.color.book_without_palette);

        return new BooksViewHolder(rowView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(final BooksViewHolder booksViewHolder, final int position) {

        final Book currentBook = books.get(position);
        //booksViewHolder.bookTitle.setText(currentBook.getCover());
        //booksViewHolder.bookAuthor.setText(currentBook.getCover());
        booksViewHolder.bookCover.setDrawingCacheEnabled(true);


        Ion.with(context)
                .load(books.get(position).getCover())
                .intoImageView(booksViewHolder.bookCover)
                .withBitmapInfo()
                .setCallback(new FutureCallback<ImageViewBitmapInfo>() {
                    @Override
                    public void onCompleted(Exception e, ImageViewBitmapInfo result) {
                        setCellColors(result.getBitmapInfo().bitmap, booksViewHolder,position);
                    }
                });

                //.setCallback(new FutureCallback<ImageViewBitmapInfo>() {
                  //  @Override
                   // public void onCompleted(Exception e, ImageViewBitmapInfo result) {

                     //   if (e == null && result != null && result.getBitmapInfo().bitmap != null) {

                       //     setCellColors(result.getBitmapInfo().bitmap, booksViewHolder, position);
                      //  }
                    //}
               // });
    }

    public void setCellColors (Bitmap b, final BooksViewHolder viewHolder, final int position) {

        Palette.generateAsync(b, new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(Palette palette) {

                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();

                if (vibrantSwatch != null) {

                    //viewHolder.bookTitle.setTextColor(vibrantSwatch.getTitleTextColor());
                    //viewHolder.bookAuthor.setTextColor(vibrantSwatch.getTitleTextColor());
                    viewHolder.bookCover.setTransitionName("cover" + position);
                    viewHolder.bookTextcontainer.setBackgroundColor(vibrantSwatch.getRgb());{
                        //@Override
                        //public void onClick(View v) {
                        //    onItemClickListener.onClick(v, position);
                        }
                   // });

                    Utils.animateViewColor(viewHolder.bookTextcontainer, defaultBackgroundcolor,
                            vibrantSwatch.getRgb());

                } else {

                    Log.e("[ERROR]", "BookAdapter onGenerated - The VibrantSwatch were null at: "+position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return books.size();
    }
}

class BooksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected  FrameLayout bookTextcontainer;
    protected  ImageView bookCover;
    protected  TextView bookTitle;
    protected  TextView bookAuthor;
    protected  ImageView extraWall;
    private  OnItemClickListener onItemClickListener;

    public BooksViewHolder(View itemView, OnItemClickListener onItemClickListener) {

        super(itemView);
        this.onItemClickListener = onItemClickListener;

//        if (itemView != null) {

        bookTextcontainer = (FrameLayout) itemView.findViewById(R.id.item_book_text_container);
        bookCover = (ImageView) itemView.findViewById(R.id.item_book_img);
        bookTitle = (TextView) itemView.findViewById(R.id.item_book_title);
        bookAuthor = (TextView) itemView.findViewById(R.id.item_book_author);
        extraWall = (ImageView) itemView.findViewById(R.id.activity_detail_cover);
        bookCover.setOnClickListener(this);
//        }

    }

    @Override
    public void onClick(View v) {

        onItemClickListener.onClick(v, getPosition());

    }
}
