package it.antanysavage.sm.player;


import it.antanysavage.sm.player.sequences.schema.types.AcceptedVideoTypes;
import it.antanysavage.sm.player.swt.views.PhotoComposite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class NewsPlayerMaster extends PlayerMaster {


	private SashForm              sashForm;
//	private NewsComposite         topNewsComposite;
//	private PlayerScreenComposite mediaComposite;
	private NewsComposite         bottomNewsComposite;
	private StackLayout mediaCompositeLayout;


	public NewsPlayerMaster(ScreenManagerComposite screenManagerComposite){
		super(screenManagerComposite);		
	}
	
	@Override
	protected Control createContents(Composite parent) {
		
		StackLayout playerCompositeLayout = new StackLayout();
		parent.setLayout(playerCompositeLayout);
		
		demoPhotoComposite = new PhotoComposite(parent);
		GridLayout demoPhotoCompositeLayout = new GridLayout();
		demoPhotoCompositeLayout.marginWidth = 0;
		demoPhotoCompositeLayout.marginHeight = 0;
		demoPhotoCompositeLayout.verticalSpacing = 0;
		demoPhotoCompositeLayout.horizontalSpacing = 0;
		demoPhotoCompositeLayout.marginTop = -1;
		demoPhotoComposite.setLayout(demoPhotoCompositeLayout);
		if ( Player.DEMO_MODE  ){
			demoPhotoComposite.set(Player.DEMO_IMAGE_MEDIA);
			demoTimer = new java.util.Timer();
		}	
		
		
		sashForm = new SashForm(parent, SWT.NONE);
		playerCompositeLayout.topControl = sashForm;
		GridLayout sashFormLayout = new GridLayout();
		sashFormLayout.makeColumnsEqualWidth = true;
		sashFormLayout.marginWidth = 0;
		sashFormLayout.verticalSpacing = 0;
		sashFormLayout.horizontalSpacing = 0;
		sashFormLayout.marginHeight = 0;
		sashForm.setLayout(sashFormLayout);
		sashForm.setOrientation(SWT.VERTICAL);
		sashForm.SASH_WIDTH = 3;
		GridData sashFormLData = new GridData();
		sashFormLData.horizontalAlignment = GridData.FILL;
		sashFormLData.verticalAlignment = GridData.FILL;
		sashFormLData.grabExcessVerticalSpace = true;
		sashFormLData.horizontalSpan = 0;
		sashFormLData.verticalSpan = 0;
		sashFormLData.grabExcessHorizontalSpace = true;
		sashFormLData.exclude = true;
		sashForm.setLayoutData(sashFormLData);
		
		
//		topNewsComposite = new NewsComposite(sashForm);
//		GridLayout topNewsCompositeLayout = new GridLayout();
//		topNewsCompositeLayout.makeColumnsEqualWidth = true;
//		topNewsCompositeLayout.marginHeight = -1;
//		topNewsCompositeLayout.horizontalSpacing = 0;
//		topNewsCompositeLayout.marginWidth = 0;
//		topNewsCompositeLayout.verticalSpacing = 0;
//		topNewsComposite.setLayout(topNewsCompositeLayout);


		programComposite = new Composite(sashForm, SWT.NONE);
		programCompositeLayout = new StackLayout();
		GridData programCompositeLData = new GridData();
		programCompositeLData.grabExcessVerticalSpace = true;
		programCompositeLData.verticalAlignment = GridData.FILL;
		programCompositeLData.horizontalAlignment = GridData.FILL;
		programCompositeLData.grabExcessHorizontalSpace = true;
		programComposite.setLayoutData(programCompositeLData);
		programComposite.setLayout(programCompositeLayout);
		
		createMediaPlayers();

		bottomNewsComposite = new NewsComposite(sashForm);
		GridLayout bottomNewsCompositeLayout = new GridLayout();
		bottomNewsCompositeLayout.makeColumnsEqualWidth = true;
		bottomNewsCompositeLayout.verticalSpacing = 0;
		bottomNewsCompositeLayout.marginWidth = 0;
		bottomNewsCompositeLayout.marginHeight = 0;
		bottomNewsCompositeLayout.horizontalSpacing = 0;
		GridData bottomNewsCompositeLData = new GridData();
		bottomNewsCompositeLData.verticalAlignment = GridData.FILL;
		bottomNewsCompositeLData.horizontalAlignment = GridData.FILL;
		bottomNewsCompositeLData.grabExcessVerticalSpace = true;
		bottomNewsCompositeLData.grabExcessHorizontalSpace = true;
		bottomNewsComposite.setLayoutData(bottomNewsCompositeLData);
		bottomNewsComposite.setLayout(bottomNewsCompositeLayout);
		


		sashForm.setBounds(0,0, parent.getClientArea().x,  parent.getClientArea().y);
		sashForm.setBackground( Display.getDefault().getSystemColor(0) ) ;
		sashForm.setSashWidth(2);
		sashForm.setWeights(new int[] {4,1});
//		setWeights( 
//				new int[] { 
//						0 , 
//						(int) (parent.getSize().y * 0.8) ,
//						(int) (parent.getSize().y * 0.2)
//					}
//		);
		
		
		/*
		 * set on top black window for programComposite
		 */
		programCompositeLayout.topControl = mediaPlayersList.get(AcceptedVideoTypes.BLACKWINDOW_TYPE).getComposite();
		programComposite.layout();
		parent.layout();
		
		
		return parent;
	}
	
	public void setWeights( final int weights[]) {
		Display.getDefault().asyncExec(
				new Runnable() {
					public void run() {
						sashForm.setWeights(weights);
					}
				}

		);
	}
	
	@Override
	public void demoSwitcher() {
		Display.getDefault().asyncExec( new Runnable() {			
			public void run() {
				if ( demoOnlineComposite == sashForm ) {
					demoOnlineComposite = demoPhotoComposite;
//					demoPhotoComposite.setDemoPhoto( Player.DEMO_IMAGE );					
//					demoPhotoComposite.resizeDemo();
				} else {
					demoOnlineComposite = sashForm;
				}
				StackLayout stackLayout = (StackLayout) getShell().getLayout();
				stackLayout.topControl = demoOnlineComposite;
				getShell().layout();
			}			
		});
	}
	
	
	public void play() {
		bottomNewsComposite.show();
		super.play();
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NewsPlayerMaster wwin = new NewsPlayerMaster(null);

		wwin.setBlockOnOpen(true);
		//		wwin.setShellStyle(SWT.SHADOW_ETCHED_IN);
		wwin.open();

		Display.getCurrent().dispose();
	}

	

}
