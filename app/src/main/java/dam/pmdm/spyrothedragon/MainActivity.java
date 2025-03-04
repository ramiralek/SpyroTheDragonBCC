package dam.pmdm.spyrothedragon;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.media.MediaPlayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import dam.pmdm.spyrothedragon.databinding.ActivityMainBinding;
import dam.pmdm.spyrothedragon.databinding.GuideEndBinding;
import dam.pmdm.spyrothedragon.databinding.GuideFirstBinding;
import dam.pmdm.spyrothedragon.databinding.GuideFourthBinding;
import dam.pmdm.spyrothedragon.databinding.GuideIntroBinding;
import dam.pmdm.spyrothedragon.databinding.GuideSecondBinding;
import dam.pmdm.spyrothedragon.databinding.GuideThirdBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    NavController navController = null;
    private GuideIntroBinding guideIntroBinding;
    private GuideFirstBinding guideFirstBinding;
    private GuideSecondBinding guideSecondBinding;
    private GuideThirdBinding guideThirdBinding;
    private GuideFourthBinding guideFourthBinding;
    private GuideEndBinding guideEndBinding;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        guideIntroBinding = binding.includeLayoutGuideIntro;
        guideFirstBinding = binding.includeLayoutFirstGuide;
        guideSecondBinding = binding.includeLayoutSecondGuide;
        guideThirdBinding = binding.includeLayoutThirdGuide;
        guideFourthBinding = binding.includeLayoutFourthGuide;
        guideEndBinding = binding.includeLayoutEndGuide;

        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(binding.navView, navController);
            NavigationUI.setupActionBarWithNavController(this, navController);
        }

        binding.navView.setOnItemSelectedListener(this::selectedBottomMenu);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.navigation_characters ||
                    destination.getId() == R.id.navigation_worlds ||
                    destination.getId() == R.id.navigation_collectibles) {
                // Para las pantallas de los tabs, no queremos que aparezca la flecha de atrás
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
            else {
                // Si se navega a una pantalla donde se desea mostrar la flecha de atrás, habilítala
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        });

        initializeGuideIntro();

    }

    private void initializeGuideIntro() {
        guideIntroBinding.startButton.setOnClickListener(this::initializeFirstGuide);

        if (sharedPreferences.getBoolean("isFirstTime", true)) {
            guideIntroBinding.guideIntroLayout.setVisibility(View.VISIBLE);

            // hacer que no salga la guia si no es la primera vez
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstTime", false);
            editor.apply();
        }
    }

    private void initializeFirstGuide(View view) {
        MediaPlayer mep = MediaPlayer.create(this, R.raw.spyro_sound);
        mep.start() ;

        guideFirstBinding.exitButton.setOnClickListener(this::onExitGuide);
        guideFirstBinding.nextButton.setOnClickListener(this::initializeSecondGuide);

        guideIntroBinding.guideIntroLayout.setVisibility(View.GONE);
        guideFirstBinding.guideLayout.setVisibility(View.VISIBLE);

        ObjectAnimator firstScaleX = ObjectAnimator.ofFloat(guideFirstBinding.pulseImage, "scaleX", 0.5f, 1f, 0.5f);
        ObjectAnimator firstScaleY = ObjectAnimator.ofFloat(guideFirstBinding.pulseImage, "scaleY", 0.5f, 1f, 0.5f);

        ObjectAnimator firstFadeInImage = ObjectAnimator.ofFloat(guideFirstBinding.pulseImage, "alpha", 0f, 1f);
        ObjectAnimator firstFadeInText = ObjectAnimator.ofFloat(guideFirstBinding.textStep, "alpha", 0f, 1f);

        firstScaleX.setRepeatCount(2);
        firstScaleY.setRepeatCount(2);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(firstScaleX).with(firstScaleY).before(firstFadeInText).after(firstFadeInImage);
        animatorSet.setDuration(1000);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
    }

    private void initializeSecondGuide(View view) {
        guideSecondBinding.exitButton.setOnClickListener(this::onExitGuide);
        guideSecondBinding.nextButton.setOnClickListener(this::initializeThirdGuide);
        binding.navView.setSelectedItemId(R.id.nav_worlds);

        guideFirstBinding.guideLayout.setVisibility(View.GONE);
        guideSecondBinding.guideLayout.setVisibility(View.VISIBLE);

        ObjectAnimator firstScaleX = ObjectAnimator.ofFloat(guideSecondBinding.pulseImage, "scaleX", 0.5f, 1f, 0.5f);
        ObjectAnimator firstScaleY = ObjectAnimator.ofFloat(guideSecondBinding.pulseImage, "scaleY", 0.5f, 1f, 0.5f);

        ObjectAnimator firstFadeInImage = ObjectAnimator.ofFloat(guideSecondBinding.pulseImage, "alpha", 0f, 1f);
        ObjectAnimator firstFadeInText = ObjectAnimator.ofFloat(guideSecondBinding.textStep, "alpha", 0f, 1f);

        firstScaleX.setRepeatCount(2);
        firstScaleY.setRepeatCount(2);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(firstScaleX).with(firstScaleY).before(firstFadeInText).after(firstFadeInImage);
        animatorSet.setDuration(1000);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
    }

    private void initializeThirdGuide(View view) {
        guideThirdBinding.exitButton.setOnClickListener(this::onExitGuide);
        guideThirdBinding.nextButton.setOnClickListener(this::initializeFourthGuide);
        binding.navView.setSelectedItemId(R.id.nav_collectibles);

        guideSecondBinding.guideLayout.setVisibility(View.GONE);
        guideThirdBinding.guideLayout.setVisibility(View.VISIBLE);

        ObjectAnimator firstScaleX = ObjectAnimator.ofFloat(guideThirdBinding.pulseImage, "scaleX", 0.5f, 1f, 0.5f);
        ObjectAnimator firstScaleY = ObjectAnimator.ofFloat(guideThirdBinding.pulseImage, "scaleY", 0.5f, 1f, 0.5f);

        ObjectAnimator firstFadeInImage = ObjectAnimator.ofFloat(guideThirdBinding.pulseImage, "alpha", 0f, 1f);
        ObjectAnimator firstFadeInText = ObjectAnimator.ofFloat(guideThirdBinding.textStep, "alpha", 0f, 1f);

        firstScaleX.setRepeatCount(2);
        firstScaleY.setRepeatCount(2);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(firstScaleX).with(firstScaleY).before(firstFadeInText).after(firstFadeInImage);
        animatorSet.setDuration(1000);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
    }

    private void initializeFourthGuide(View view) {
        guideFourthBinding.exitButton.setOnClickListener(this::onExitGuide);
        guideFourthBinding.nextButton.setOnClickListener(this::initializeEndGuide);

        //MenuItem item = findViewById(R.id.action_info);
        //onOptionsItemSelected(item);

        guideThirdBinding.guideLayout.setVisibility(View.GONE);
        guideFourthBinding.guideLayout.setVisibility(View.VISIBLE);

        ObjectAnimator firstScaleX = ObjectAnimator.ofFloat(guideFourthBinding.pulseImage, "scaleX", 0.5f, 1f, 0.5f);
        ObjectAnimator firstScaleY = ObjectAnimator.ofFloat(guideFourthBinding.pulseImage, "scaleY", 0.5f, 1f, 0.5f);

        ObjectAnimator firstFadeInImage = ObjectAnimator.ofFloat(guideFourthBinding.pulseImage, "alpha", 0f, 1f);
        ObjectAnimator firstFadeInText = ObjectAnimator.ofFloat(guideFourthBinding.textStep, "alpha", 0f, 1f);

        firstScaleX.setRepeatCount(2);
        firstScaleY.setRepeatCount(2);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(firstScaleX).with(firstScaleY).before(firstFadeInText).after(firstFadeInImage);
        animatorSet.setDuration(1000);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
    }

    private void initializeEndGuide(View view) {
        guideEndBinding.startButton.setOnClickListener(this::onFinishedGuide);

        guideFourthBinding.guideLayout.setVisibility(View.GONE);
        guideEndBinding.guideEndLayout.setVisibility(View.VISIBLE);
    }

    private void onFinishedGuide(View view) {
        MediaPlayer mep = MediaPlayer.create(this, R.raw.spyro_sound);
        mep.start() ;
        guideEndBinding.guideEndLayout.setVisibility(View.GONE);
    }

    private void onExitGuide(View view) {
        guideFirstBinding.guideLayout.setVisibility(View.GONE);
        guideSecondBinding.guideLayout.setVisibility(View.GONE);
        guideThirdBinding.guideLayout.setVisibility(View.GONE);
        guideFourthBinding.guideLayout.setVisibility(View.GONE);
    }

    private boolean selectedBottomMenu(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_characters)
            navController.navigate(R.id.navigation_characters);
        else
        if (menuItem.getItemId() == R.id.nav_worlds)
            navController.navigate(R.id.navigation_worlds);
        else
            navController.navigate(R.id.navigation_collectibles);
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla el menú
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Gestiona el clic en el ítem de información
        if (item.getItemId() == R.id.action_info) {
            showInfoDialog();  // Muestra el diálogo
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showInfoDialog() {
        // Crear un diálogo de información
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_about)
                .setMessage(R.string.text_about)
                .setPositiveButton(R.string.accept, null)
                .show();
    }



}