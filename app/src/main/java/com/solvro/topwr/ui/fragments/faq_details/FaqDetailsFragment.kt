package com.solvro.topwr.ui.fragments.faq_details

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.solvro.topwr.R
import com.solvro.topwr.data.model.info.Info
import com.solvro.topwr.databinding.FaqDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FaqDetailsFragment : Fragment(R.layout.faq_details_fragment) {

    companion object {
        fun newInstance() = FaqDetailsFragment()
    }

    @Inject
    lateinit var glide: RequestManager

    private lateinit var binding: FaqDetailsFragmentBinding
    private val viewModel: FaqDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSharedTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FaqDetailsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val info = viewModel.info.value

        info?.let {
            setTransitionNames(it)
        }

        binding.apply {
            //temporary
//            faqDescription.text = info?.description
            val exampleText = "<body><h1>Kto może pierwszy otrzymać pokój?</h1>Pierwszeństwo w otrzymaniu pokoju mają osoby, ktrórym codzienny dojazd do uczelnii sprawił by trudności w studiowaniu. <a href=\"http://www.google.com\">This is a link</a> W skrócie rzecz ujmując, zamieszkują daleko od uczelni. We wniosku o miejsce w akademiku trzeba podać nazwe miejsca zamieszkania, a także odległość w kilometrach od uczelni. <br><br>Osoby z problemami materialnymi także posiadają prawo pierwszeństwa w otrzymaniu miejsca w akademiku. We wniosku jest obliczany średni miesięczny dochód<br>netto na osobę w rodzinie, i na tej podestawie jest okreslana sytuacja materialna studenta. Dodatkowo brane są pod uwage stypendia specjalne. <br><br><b>Okres wynajmowania pokoju</b><br>Miejsce w domu studenckim jest przyznawane studentowi od momentu rozpoczecia studiów aż do zakonczenia sesji letnij w danym roku. Wszelkie odchylenia od tej <span style='color:green'> reguły są zawarte w regulaminie </span> domów studenckich.  <a href ='http://samorzad.pwr.edu.pl/fcp/8GBUKOQtTKlQhbx08SlkTVgJQX2o8DAoHNiwFE1wZDyEPG1gnBVcoFW8SBDRKTxMKRy0SODwBBAEIMQheCFVAORFCHzY/56/public/pomoc_materialna/akademiki/zasady-zamieszkiwania-w-domach-studeckich.pdf'> Link przykładowy </a> <br><br>Jak otrzymać miejsce w domach studenckich?<br>Musisz złożyć wniosek o miejsce w domu studenckim za pośrednictwem systemu EDUKACJA CL. Terminy składania wniosków są ustalane przez Dział Domów Studenckich. Daty te są zawsze ogłaszane na stronie internetowej <a  href=\"https://prs.pwr.edu.pl/?page_id=529\">Terminy wniosków</a> <br><br>Po wypełnieniu wniosku i jego przesłaniu czekasz na decyzje. <span style='color:red'>  Jeśli wniosek nie otrzyma zgody możesz złożyć kolejny wniosek z odwołaniem decyzji. </span> Jest to szansa na uargumentowanie dlaczego powinieneś otrzymać miejsce w domu studenckim.</body>"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                faqDescription.text = Html.fromHtml(exampleText, Html.FROM_HTML_MODE_LEGACY)
            } else {
                faqDescription.text = Html.fromHtml(exampleText)
            }

            (faqDescription as TextView).movementMethod = LinkMovementMethod.getInstance()


            backToFaq.setOnClickListener {
                startPostponedEnterTransition()
                findNavController().navigateUp()
            }
        }

        info?.photo?.url?.let { loadImage(it) }
    }

    private fun loadImage(imageUrl: String) {
        glide.load(imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

            })
            .into(binding.faqImageView)
    }

    private fun setTransitionNames(info: Info) {
        binding.apply {
            faqImageView.transitionName = getString(R.string.faq_image, info.id)
        }
    }

    private fun setupSharedTransition() {
        sharedElementEnterTransition = TransitionInflater.from(context!!)
            .inflateTransition(R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(context!!)
            .inflateTransition(R.transition.move)
    }
}