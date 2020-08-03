package sen.com.sce.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.f_part_update_bos.*
import sen.com.sce.R
import sen.com.sce.manager.DataManager
import sen.com.sce.model.ExtraPart
import sen.com.sce.model.getPartDrawable
import sen.com.sce.utils.toRupiah
import sen.com.sce.utils.watchMoney
import sen.com.sce.utils.watchText

/**
 * Created by korneliussendy on 02/08/20,
 * at 16.32.
 * SCE
 */
class FPartUpdateBos : BottomSheetDialogFragment() {
    private val manager by lazy { DataManager(requireContext()) }
    var extraPart: ExtraPart? = null
    var onPartUpdated: ((part: ExtraPart?) -> Unit)? = null
    private var mode: MODE = MODE.EDIT

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.InputDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.f_part_update_bos, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (extraPart == null) {
            mode = MODE.ADD
            extraPart = ExtraPart()
            btnSubmit.text = "Tambah"
        } else {
            mode = MODE.EDIT
            btnSubmit.text = "Ubah"
        }
        initData()
        etName.watchText { extraPart?.name = it }
        etPrice.watchMoney { extraPart?.price = it }
        btnSubmit.setOnClickListener { submitPart() }
    }

    private fun initData() {
        etName.setText(extraPart?.name)
        ivIcon.setImageDrawable(extraPart?.getPartDrawable(requireContext()))
        etPrice.setText(extraPart?.price.toRupiah())
    }

    private fun submitPart() {
        if (extraPart?.name.isNullOrEmpty()) {
            etName.error = "Masukkan nama"
            return
        } else {
            etName.error = null
        }
        if (extraPart?.price ?: 0.0 < 0) {
            etPrice.error = "Invalid"
            return
        } else {
            etPrice.error = null
        }
        extraPart?.let {
            manager.saveExtraPart(it)
            onPartUpdated?.invoke(extraPart)
            dismissAllowingStateLoss()
        }
    }
}


private enum class MODE {
    ADD, EDIT
}