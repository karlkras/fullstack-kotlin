@file:Suppress("UnsafeCastFromDynamic")

package com.packtpub.components

import com.packtpub.components.form.Form
import com.packtpub.model.Project
import com.packtpub.store.ActionType
import com.packtpub.store.FormInput
import com.packtpub.store.ReduxStore
import com.packtpub.store.submitForm
import com.packtpub.util.js
import com.packtpub.util.jsObject
import react.RProps
import react.ReactComponentStatelessSpec
import react.dom.ReactDOMBuilder
import react.dom.ReactDOMStatelessComponent
import redux.ReduxAction
import redux.asConnectedComponent
import redux.connect

val routerComponent =
    connect<RouterComponent.Props, ReduxStore>(
        { state: ReduxStore, _ ->
            jsObject {
                hash = state.hash
                currentProject = state.currentProject
                projectList = state.projectList
                isSpinning = state.isSpinning
            }
        }, { dispatch, _ ->
        jsObject {
            updateAction = { target, value ->
                dispatch(ReduxAction(ActionType.FORM_INPUT, FormInput(target, value))())
            }
            submitAction = { project ->
                js {
                    dispatch(submitForm(project))
                }
            }
            clearAction = { dispatch(ReduxAction(ActionType.FORM_CLEAR)()) }
        }
    })


class RouterComponent : ReactDOMStatelessComponent<RouterComponent.Props>() {
    companion object : ReactComponentStatelessSpec<RouterComponent, Props>


    override fun ReactDOMBuilder.render() {
        when (props.hash) {
            "form" ->
                Form {
                    update = props.updateAction
                    submit = { props.submitAction(props.currentProject) }
                    clear = props.clearAction
                    project = props.currentProject
                }
            else   -> {
                ProjectList.asConnectedComponent(projectList).invoke()
            }
        }
    }

    class Props(var hash: String = "",
                var projectList: Array<Project> = arrayOf(),
                var isSpinning: Boolean = false,
                var currentProject: Project = Project.identity(),
                var updateAction: (Any, String) -> Unit,
                var clearAction: () -> Unit,
                var submitAction: (Project) -> Unit) : RProps()
}

