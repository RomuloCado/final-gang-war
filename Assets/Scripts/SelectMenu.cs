using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class SelectMenu : MonoBehaviour {

	public Image adamImage;
	public Animator adamAnim;

	private Color defaultColor;
	private int characterIndex;
	private AudioSource audioS;
	// Use this for initialization
	void Start () {

		characterIndex = 1;
		audioS = GetComponent<AudioSource>();
		defaultColor = adamImage.color;
	}
	
	// Update is called once per frame
	void Update () {

		if (Input.GetKeyDown(KeyCode.LeftArrow))
		{
			characterIndex = 1;
			PlaySound();

		}
		else if (Input.GetKeyDown(KeyCode.RightArrow))
		{
			characterIndex = 2;
			PlaySound();
		}

		if(characterIndex == 1)
		{
			adamImage.color = Color.yellow;
			adamAnim.SetBool("Attack", true);
		}

		if (Input.GetKeyDown(KeyCode.Return))
		{
			FindObjectOfType<GameManager>().characterIndex = characterIndex;
			SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex + 1);
		}
	}

	void PlaySound()
	{
		if (!audioS.isPlaying)
		{
			audioS.Play();
		}
	}
}
